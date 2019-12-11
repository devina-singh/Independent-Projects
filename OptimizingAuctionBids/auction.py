#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Jul  6 14:36:56 2017

@author: Devina

This program creates and runs an auction to test the new low regret algorithm on
"""

import numpy as np
import math
import seaborn as sns
import matplotlib.pyplot as plt
import matplotlib.mlab as mlab


p1 = Buyer() # player 1 
p2 = Buyer() # player 2

num_trials  = 10
value = 0.5 

for i in range(num_trials):
    bid1 = p1.player(value)
    #print("bid1")
    #print(bid1)
    bid2 = p2.player(value)
    #print("bid2")
    #print(bid2)
    diff = math.fabs(bid1 - bid2) # the difference in bids => reward 
    if bid1 > bid2:
        p1.win_update(value, bid1, reward = diff)
    elif bid2 > bid1:
        p2.win_update(value, bid2, reward = diff)
    else: # in the event of a tie
        p1.win_update(value, bid1, reward = diff)
    
    
fig, ax = plt.subplots()
x = np.arange(0, p1.num_arms(), 1)
ax.hlines(x, [0], p1.return_weights(value), lw = 2)
print(np.ravel(p1.return_weights(value)))
