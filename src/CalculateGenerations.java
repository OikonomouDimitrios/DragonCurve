
public class CalculateGenerations {

    private static final String X = "X";
    private static final String Y = "Y";
    private static final String rule1 = "X+YF+";
    private static final String rule2 = "-FX-Y";


    public static String applyRule(int iterations) {
        var startingString = "FX";
        var newPattern = "";

        for (int j = 0; j < iterations; j++) {
            var strArray = startingString.split("");
            for (int i = 0;
                 i < strArray.length; i++) {
                switch (strArray[i]) {
                    case X:
                        newPattern += rule1;
                        break;
                    case Y:
                        newPattern += rule2;
                        break;
                    default:
                        newPattern += strArray[i];
                        break;
                }
                startingString = newPattern;

            }
        }
        return newPattern;
    }

}
