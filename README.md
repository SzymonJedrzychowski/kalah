# kalah

# About

This software is an attempt to create smart and optimised opponents for game Kalah (find out more: https://en.wikipedia.org/wiki/Kalah).

# Requirements
Software was created using JDK17.

# Currently available agents
- minimax opponent,
- minimax with alpha-beta pruning,
- Monte Carlo Tree Search.

# Performance of models
Small test was conducted to find the performance improvement of abPruning opponent compared to Minimax opponent. 5 games were played (with the same moves), each 3 times, and average time spent on choosing move and number of created states was shown in the table bellow.
![image](https://user-images.githubusercontent.com/47495079/191841823-cc1702e1-9b9a-46ca-8865-d6a4ec2b503c.png)

Overall performance:
![image](https://user-images.githubusercontent.com/47495079/191841896-34e758fc-fec8-4395-94b2-8c75495b01fd.png)
