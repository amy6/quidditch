# quidditch

**Google Udacity Scholarship Challenge**
Here is my version of the CourtCounter App - a Harry Potter Quidditch game app - the one that’s played with two balls here (quaffle and snitch) and with players on broomsticks:

APP SCREENSHOTS:
Initial state of the app:

![initial_state](https://user-images.githubusercontent.com/5392993/37608186-e875a040-2bbf-11e8-94c0-c3646dbdbd47.jpg)

Every quaffle ball sent through the hoops will add 10 points to the team. Every foul from a team will award 5 points to the opponent team. The score text is shown in green for the team that is leading and red for the team that is lagging.

![quaffle_score](https://user-images.githubusercontent.com/5392993/37608212-f69a2182-2bbf-11e8-92cd-019eda843e38.jpg)   ![foul_score_opponent](https://user-images.githubusercontent.com/5392993/37608181-e58bb752-2bbf-11e8-86c3-5b038f0bdaee.jpg)

Scores are displayed in black when they are equal.

![equal_score](https://user-images.githubusercontent.com/5392993/37608170-e1fc1938-2bbf-11e8-84b2-1a71494e6429.jpg)

Game ends when the snitch is caught. The team that caught the snitch gets 150 points. Whoever has scored most points would be declared as the winner once the snitch has been caught. A message is displayed stating the winner.

![snitch_caught](https://user-images.githubusercontent.com/5392993/37608217-fc0cc3f4-2bbf-11e8-8e0a-c49cc7875bf9.jpg)

The scores are preserved when the screen orientation changes.

![landscape_orientation](https://user-images.githubusercontent.com/5392993/37608196-ee02266e-2bbf-11e8-9520-ad894ec058b0.jpg)

Clicking on reset would set the scores to 0 (as in initial state screenshot). Clicking on any buttons after the game has finished without resetting the score will reset the scores automatically and display a toast “New game”.

![new_game](https://user-images.githubusercontent.com/5392993/37608203-f273fd26-2bbf-11e8-86a1-67cfb5b2a826.jpg)
