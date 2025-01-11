import java.io.*;
import java.util.*;

public class Main {
    private String[] rsids;
    private String[] genotypes;

    private void process() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename: ");
        String filename = scanner.nextLine();

        parseFile(filename);

        String skinTypeResult = checkSkinType(rsids, genotypes);
        System.out.println("Skin Type: " + skinTypeResult);

        String diabetesRiskResult = checkType2Diabetes(rsids, genotypes);
        System.out.println("Diabetes Risk: " + diabetesRiskResult);

        String cancerRiskResult = checkCancer(rsids,genotypes);
        System.out.println("Cancer Risk: " + cancerRiskResult);

        String exerciseResult = checkExercise(rsids, genotypes);
        System.out.println("Exercise Privilege: " + exerciseResult);

        String dietResult = checkDiet(rsids, genotypes);
        System.out.println("Diet Privilege: " + dietResult);
    }

    private void parseFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        List<String> rsidList = new ArrayList<>();
        List<String> genotypeList = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("rs")) {
                String[] parts = line.split("\\s+");
                rsidList.add(parts[0]);
                genotypeList.add(parts[3]);
            }
        }

        rsids = rsidList.toArray(new String[0]);
        genotypes = genotypeList.toArray(new String[0]);

        br.close();
    }

    private String checkSkinType(String[] rsids, String[] genotypes) {
        for (int i = 0; i < rsids.length; i++) {
            if (rsids[i].equals("rs4477212")) {
                if (genotypes[i].equals("AA")) {
                    return "Probably light-skinned, European ancestry";
                }
            } else if (rsids[i].equals("rs3094315")) {
                if (genotypes[i].equals("AA")) {
                    return "Probably mixed African/European ancestry";
                }
            }
        }
        return "No DNA info on Skin Type";
    }

    private String checkType2Diabetes(String[] rsids, String[] genotypes) {
        for (int i = 0; i < rsids.length; i++) {
            if (rsids[i].equals("rs4477212")) {
                if (genotypes[i].equals("AA") || genotypes[i].equals("CG")) {
                    return "1.3x Increased risk for Type-2 Diabetes";
                }
            } else if (rsids[i].equals("rs3094315")) {
                if (genotypes[i].equals("CC") || genotypes[i].equals("GG")) {
                    return "Normal risk for Type-2 Diabetes";
                }
            }
        }
        return "No DNA info on Type-2 Diabetes";
    }

    private String checkExercise(String[] rsids, String[] genotypes) {
        boolean hasRs4994 = false;
        boolean hasRs1042713 = false;

        for (int i = 0; i < rsids.length; i++) {
            if (rsids[i].equals("rs4994") && (genotypes[i].equals("AA") || genotypes[i].equals("TT"))) {
                hasRs4994 = true;
            }
            if (rsids[i].equals("rs1042713") && (genotypes[i].equals("AA") || genotypes[i].equals("TT"))) {
                hasRs1042713 = true;
            }
        }

        if (hasRs4994 && hasRs1042713) {
            return "12% Genetic Privilege: Any Exercise Will Help You Lose Weight";
        } else {
            return "88% Genetic Disprivilege: Only High Intensity Workouts Will Help You Lose Weight";
        }
    }

    private String checkDiet(String[] rsids, String[] genotypes) {
        boolean hasrsid1799883 = false;
        boolean hasrsid1801282 = false;
        boolean hasrsid1042714 = false;

        for (int i = 0; i < rsids.length; i++) {
            if (rsids[i].equals("rs1799883") && (genotypes[i].equals("GG"))) {
                hasrsid1799883 = true;
            }
            if (rsids[i].equals("rs1801282") && (genotypes[i].equals("CC"))) {
                hasrsid1801282 = true;
            }
            if (rsids[i].equals("rs1042714") && (genotypes[i].equals("CC"))) {
                hasrsid1042714 = true;
            }
        }

        if (hasrsid1801282 && hasrsid1042714) {
            return "16% Genetic privilege: Any Diet Works For You";
        }
        String exerciseResult = checkExercise(rsids, genotypes);
        if (exerciseResult.equals("88% Genetic Disprivilege: Only High Intensity Workouts Will Help You Lose Weight") && hasrsid1801282 && !hasrsid1799883) {
            return "39% Genetic Disprivilege: You Will Lose 2.5x As Much Weight on a Low Fat Diet";
        }
        if (exerciseResult.equals("12% Genetic Privilege: Any Exercise Will Help You Lose Weight") && (!hasrsid1801282 || !hasrsid1042714)) {
            return "39% Genetic Disprivilege: You Will Lose 2.5x As Much Weight on a Low Fat Diet";
        }
        if (!hasrsid1801282 && !hasrsid1042714) {
            return "45% Genetic Disprivilege: You Will Lose 2.5x As Much Weight On a Low Carb Diet";
        }
        return "No Diet Privilege or Disprivilege";
    }

    private String checkCancer(String[] rsids, String[] genotypes) {
        for (int i = 0; i < rsids.length; i++) {
            if (rsids[i].equals("1800566")) {
                if (genotypes[i].equals("CT")) {
                    return "0.48x Lower Lung Cancer Risk";
                }
            }else if (genotypes[i].equals("TT")) {
                    return "Benzene toxcity, Up to 1.4x Higher Risk of Cancer";
                }
            }
        return "No DNA info on Cancer risks";
        }


    public static void main(String[] argv) throws IOException {
        Main dnaResults = new Main();
        dnaResults.process();
    }
}