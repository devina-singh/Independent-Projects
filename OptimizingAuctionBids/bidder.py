#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Jul  3 11:13:03 2017

@author: Devina
This program simulates the buyers who will employ the new low regret version of the Exp3 algorithm in the auction modeled below. The buyers represent the arms in a strategic multi-armed bandit model. 
"""

import numpy as np
import math
import seaborn as sns
import matplotlib.pyplot as plt

class Bidder(object):
    """
    Initializes eleven arms & the sample space 
    from which values will be drawn. Weights are 
    initialzied, created for each instance of the 
    algorithm being used 
    """
    def __init__(self, num_arms = 11):
        self.num_arms = num_arms
        self.sample_space = np.linspace(0, 1, num = self.num_arms)
        self.parameter = np.random.uniform() #denoted as gamma in the EXP3 paper
        
        while self.parameter == 0:
            self.parameter = np.random.uniform()
        
        #self.parameter = 0.67

        self.weights = {}
        self.prob = {}
        # constant = ((1 - self.parameter)/len(self.sample_space)) + (self.parameter/self.num_arms)
        
        val = ((1 - self.parameter)/len(self.sample_space)) + (self.parameter/self.num_arms)
            
        for i in range(len(self.sample_space)):
            self.weights[i] = np.ones(len(self.sample_space)) # initializes an array of weights 
            print(np.ravel(self.weights[i]))
            self.prob[i] = np.full(len(self.sample_space), fill_value = val) # sets distinct probabilties 
            print(np.ravel(self.prob[i]))
        
        """
        Draws a random value from the sample space
        """
    def draw(self):
        indicie = np.random.randint(0, high = 10 + 1) 
        return self.sample_space[indicie]
        
        # Returns the number of arms
    def num(self):
        return self.num_arms
        
        
        # Returns the bid/arm selected
    def player(self, value): 
        index = 0
        for i in range(len(self.sample_space)):
            if self.sample_space[i] == value:
                index = i 
        
        # Calculates new set of probabilities 
        numerator = 1 - self.parameter
        denominator = sum(self.weights[index])
        term = self.parameter/self.num_arms
        
        probabilites = [] 
        probabilites = np.empty_like(self.weights[index]) #empty array w/ same shape
        
        #for i in range(self.num_arms):
        for i in range(len(self.weights[index])):
            probabilites[i] = (((numerator * self.weights[index][i])/denominator) + term)
        
        self.prob[index] = probabilites #update
        bid = np.random.choice(self.sample_space, p = self.prob[index])
        return bid 

        # applys the update rule 
    def win_update(self, value, bid, reward, won = True):
        index_set = 0
        index_weight = 0
        
        for i, j in enumerate(self.sample_space): #FIX
            if j == bid:
                index_set = i # to find the set of weights for that instance
            if j == value:
                index_weight = i #finds the index of the weight in that instance 
        
        prob = self.prob[index_set][index_weight]
        if won:
            reward /= prob  
            self.weights[index_set][index_weight] *= np.exp(((self.parameter * reward)/self.num_arms))


            #returns an array of weights associated with some value in the distribution 
    def return_weights(self, val):
        weights = []
        for i, j in enumerate(self.sample_space):
            if j == val:
                weights = self.weights[i]
        return weights
        
