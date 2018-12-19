# aviv project
This is project to my university assignment.
ex3 is "game" in style packmans. the board of game is Areial university map. Inside have fruit and the packmans eat them.
ex2 is system to get geography information and save it.
Later this system make more thing.
## ex2
![menu](https://raw.githubusercontent.com/aviv1620/OOP_EX2-Ex4/master/Manual.jpg)
### play the game
Go to main method in **GUI.MyFrame** class.
All buttons explained in manual [click hare](https://github.com/aviv1620/OOP_EX2-Ex4/wiki/Manual).

### class diagram
Recommended start look in [Gui.gif](diagram/Gui.gif) from hare have link to all the diagram.

### shortest algorithm explanation
in **algorithms.ShortestPathAlgo** class. if have RTL(right to left) problem read the [algorithm explanation.docx](algorithm_explanation.docx).
<p dir='rtl' align='right'>
אלגוריתם זה מבוסס על Greedy עם שתי שיפורים.
תחילה אני מבצע Greedy כלומר הולך לפקמן האשון וניגש לפרי הכי קרוב עליו. אחר-כך ניגש לשני והולך הכי קרוב עליו. כך הלאה עד שכול הפרות נאכלים. זה מתבצע במתודה runGreedyAlgorithm.
לאחר מכן השיפור האשון הוא שאני הולך לפקמנים שהמסלול שלהם לקח הרבה זמן ובודק אם אפשר להעביר מהם פרי לפקמנים שהמסלול שלהן לקח פחות זמן. פעולה זו מאזנת את העומס בין כול הפקמנים. הסיבה לקח שהמסלולים לא מאוזנים מתכלילה היא שלפעמים פאקמן אחד יותר מהיר מהפקמן השני. שיפור זה מתחיל מהמתודה runImproveAlgorithms.
השיפור השני הוא שבמקרים מסוימים הפקמן מסיים מאין קוו מסויים של נקודות ולאחר מכן הוא נאלץ לעבור קטע מאוד ארוך בשביל להגיע לנקודה אחרת. דוגמא למקרה זה יש בקובץ game_1543693911932_a.כול עוד הבוליאן specificCases מופעל ב- Greedyמדלגים על פקמן במידה והמרחק גדול מדי. במידה ואין ברירה והאלגוריתם מדלג כול הזמן על כול הפקמנים ונכנס ללולאה אין סופית יש משתנה בשם ttl שדואג שבסוף הפקמן כן ישלח למרחק הזה.</p>

## ex2
main class:
### coordinates calculator
class hold method to calculate in polar coordinates.
this class test in class MyCoordsTest in junit system.

### CSV to kml
convert csv file that make in the application WigleWifi to kml file.
possible to **show kml file in goolge earth and the time line**.
**to convert** run the convert method and add the name of the file.

### multCSV
Get folder name ,scan Recursive and fine any csv file.
Build layer to any csv file.
Build project store all the layers.

## javadoc
[hare](doc/index.html) the javadoc.




