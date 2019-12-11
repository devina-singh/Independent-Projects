from qiskit import *
import os
import glob
import sys
import json
from qiskit.transpiler import PassManager, transpile
from qiskit.transpiler.passes import ResourceEstimation

# Add config directory to filepath
sys.path.append(os.getcwd()[:-7] + 'config')
import Config_IBMQ_experience
from qiskit.transpiler.passes import resource_estimation


# Get filepaths
filepath = os.getcwd()
benchmarks_filepath = filepath[:-7] + 'benchmarks'
root = filepath[:-24]
scaf_filepath = root + "scaffold.sh"

print("\nRunning NISQ Test Benchmarks")
print("===================================")

# Connect to IBMQ and retrieve backend simulator
IBMQ_token = Config_IBMQ_experience.API_token #token needs to be added to file in config directory
IBMQ_URL = Config_IBMQ_experience.API_URL
IBMQ.enable_account(IBMQ_token, IBMQ_URL)

backend_sim = IBMQ.get_backend('ibmq_qasm_simulator')
correct_tests = 0
total_tests = 0

# Compile all the .scaffold files to open QASM and run them on the simulator to generate output
for path, subdir, files in os.walk(benchmarks_filepath):
	for file_name in files:
		if (file_name.endswith(".scaffold")):
			# Get file path of scaffold file and compile it using scaffold.sh
			file_path = benchmarks_filepath + "/" + file_name[:-9] + "/" + file_name
			os.system(scaf_filepath + " -b " + file_path)
			qasm_file = benchmarks_filepath + "/" + file_name[:-9] + "/" + file_name[:-8] + "qasm"
			
			# If open QASM can't be found then there was an error compiling the scaffold file
			if not glob.glob(qasm_file):
				print("Error Compiling Scaffold File - Could not Find QASM File")
				sys.exit(1)

			qc = QuantumCircuit.from_qasm_file(qasm_file)

			#Generate Resource Estimation from IBMQ simulator
			print('\033[1m' + "Generating " + file_name[:-5] + " Resource Estimation" + '\033[0m')
			passmanager = PassManager()
			passmanager.append(ResourceEstimation())
			resources = qiskit.compiler.transpile(qc, backend_sim, pass_manager=passmanager)
			print("Size: " + str(passmanager.property_set['size']) + "; Depth: " + str(passmanager.property_set['depth']) + "; Width: " + str(passmanager.property_set['width']) + "; Count Ops: " + str(passmanager.property_set['count_ops']))

			# Getting output counts
			rint('\033[1m'+ "Testing " + file_name[:-5] + " measurement output ---" + '\033[0m', end =" ")
			job_sim = execute(qc, backend_sim) # default is 1024 shots
			result_sim = job_sim.result()
			counts_sim = result_sim.get_counts(qc)
			counts = json.dumps(counts_sim).split("\"")[1]

			# Compare output counts
			benchmark_file = benchmarks_filepath + "/" + file_name[:-5] + "/" + file_name[:-5] + ".counts"
			counts_file = open(benchmark_file, "r")
			benchmark_measurement = counts_file.readline()
			print(counts)
			if counts == benchmark_measurement:
				print('\033[92m' + "  passed" + '\033[0m' + '\n')
				correct_tests = correct_tests + 1
			else:
				print('\033[91m' + " failed - " + str(counts) +" measurement is not " + benchmark_measurement + '\033[0m' + '\n')
			total_tests = total_tests + 1


print("\n===================================")
if (correct_tests == total_tests):
	print('\033[92m' + str(correct_tests) + "/" + str(total_tests) + " tests passed" + '\033[0m')
else:
	print('\033[91m' + str(correct_tests) + "/" + str(total_tests) + " tests passed"  + '\033[0m')



IBMQ.disable_accounts(token=IBMQ_token)


