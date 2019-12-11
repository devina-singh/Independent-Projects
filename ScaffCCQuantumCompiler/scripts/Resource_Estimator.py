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

import argparse

IBMQ_token = Config_IBMQ_experience.API_token
IBMQ_URL = Config_IBMQ_experience.API_URL

IBMQ.enable_account(IBMQ_token, IBMQ_URL)
backend_sim = IBMQ.get_backend('ibmq_qasm_simulator')

parser = argparse.ArgumentParser(description='Estimate Resources for OpenQASM File')
parser.add_argument('-f', action="store", dest="file_name")
print(parser.parse_args().file_name)

filename = parser.parse_args().file_name

if not filename:
	print("OpenQASM filename required")
	exit()
qc = QuantumCircuit.from_qasm_file(filename)

passmanager = PassManager()
passmanager.append(ResourceEstimation())
resources = qiskit.compiler.transpile(qc, backend_sim, pass_manager=passmanager)
print("Size: " + str(passmanager.property_set['size']) + "; Depth: " + str(passmanager.property_set['depth']) + "; Width: " + str(passmanager.property_set['width']) + "; Count Ops: " + str(passmanager.property_set['count_ops']))

IBMQ.disable_accounts(token=IBMQ_token)
