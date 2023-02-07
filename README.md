# CS361_Project1
Github Repo for the first project of BSU CS361 - Intro to Theory of Computation. 

Authors: Andrew Lackey, Braeden LaCombe

Date: February 6th, 2023

Program allows user to create simple finite automata machine from input. Currently, and as described by the assignment specifications, there is no "main method" where the user can run the program and decide what input to enter. The only way to harness this program is to use the provided testing suite and change the tests that are in the suite. That being said, the tester can be ran by:
1. Download this repo to your local machine and use your code editor of choice (this will provide instructions for VSCode).
2. Once open in VSCode, navigate to the left of your screen and click on the icon that looks like a chemical flask.
3. If any files/extensions for "JUnit" are needed it will prompt you to download them.
4. Once any needed extensions are installed, click on the play button at the top left of your screen to run the tester.
5. Drop down the arrows of each test to see what tests passed or failed. 
6. There is also a play button that allows you to run the tester in debug mode.


Program Creation reflections:

We were able to get all the provided test cases to pass except for the last two toString() testers (test2_4 and test3_4). We are unsure why those two last ones failed because the string that is returned is exactly the same as the expected String in the tester when debugging those tests. You can see that our program is returning the correct string when compared to the tester in this screenshot:

![Capture](https://user-images.githubusercontent.com/77242875/217122042-e493a8e3-95a1-443f-91d1-473fa6a1d60a.PNG)


So we aren't sure why the these tests are failing but we don't think its our program's fault...



NOTE: This program strictly allows for Deterministic Automata only, it will not work for non-deterministic simulations.




