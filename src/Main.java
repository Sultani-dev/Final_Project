import comparators.PaperComparators;
import exceptions.LowHIndexException;
import exceptions.NonResearcherException;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Professor drSmith  = new Professor("Dr. Smith", 7);
        Professor drLow    = new Professor("Dr. Low",   1);
        GradStudent alice  = new GradStudent("Alice",   4);
        GradStudent bob    = new GradStudent("Bob",     2);

        ResearchPaper p1 = new ResearchPaper(
                "Deep Learning for NLP",
                Arrays.asList("Dr. Smith", "Alice"),
                "IEEE Transactions on Neural Networks",
                LocalDate.of(2019, 3, 15),
                320, 12, "10.1109/TNN.2019.001", "Abstract A", "AI, NLP"
        );
        ResearchPaper p2 = new ResearchPaper(
                "Federated Learning at Scale",
                Arrays.asList("Dr. Smith"),
                "Nature Machine Intelligence",
                LocalDate.of(2022, 7, 1),
                85, 8, "10.1038/NMI.2022.042", "Abstract B", "FL, Privacy"
        );
        ResearchPaper p3 = new ResearchPaper(
                "Graph Neural Networks Survey",
                Arrays.asList("Alice", "Bob"),
                "ACM Computing Surveys",
                LocalDate.of(2021, 11, 20),
                540, 24, "10.1145/ACM.2021.099", "Abstract C", "GNN, Graph"
        );
        ResearchPaper p4 = new ResearchPaper(
                "Quantum Computing Basics",
                Arrays.asList("Bob"),
                "Physical Review Letters",
                LocalDate.of(2020, 5, 10),
                60, 6, "10.1103/PRL.2020.011", "Abstract D", "Quantum"
        );

        drSmith.addPaper(p1);
        drSmith.addPaper(p2);
        alice.addPaper(p1);
        alice.addPaper(p3);
        bob.addPaper(p3);
        bob.addPaper(p4);

        System.out.println("\n--- Dr. Smith's papers sorted by DATE ---");
        drSmith.printPapers(PaperComparators.BY_DATE);

        System.out.println("--- Alice's papers sorted by CITATIONS ---");
        alice.printPapers(PaperComparators.BY_CITATIONS_DESC);

        System.out.println("--- Bob's papers sorted by PAGES ---");
        bob.printPapers(PaperComparators.BY_PAGES);

        ResearchProject project = new ResearchProject("AI in Healthcare");
        project.addPaper(p1);
        project.addPaper(p3);
        project.addPaper(p2);

        try {
            project.addParticipant(drSmith);
            project.addParticipant(alice);
            project.addParticipant("John (Bachelor Student)"); // will throw
        } catch (NonResearcherException e) {
            System.out.println("\n " + e.getMessage());
        }

        project.printParticipants();
        project.printPapers(PaperComparators.BY_CITATIONS_DESC);

        System.out.println("--- Supervisor Validation ---");
        try {
            drSmith.validateAsSupervisor();
            System.out.println(drSmith.getName() + " approved as supervisor.");
            drLow.validateAsSupervisor(); // will throw
        } catch (LowHIndexException e) {
            System.out.println(e.getMessage());
        }

        List<Researcher> allResearchers = new ArrayList<>(
                Arrays.asList(drSmith, alice, bob)
        );
        ResearchManager manager = new ResearchManager(allResearchers);

        System.out.println("\n--- ALL university papers sorted by DATE ---");
        manager.printAllPapers(PaperComparators.BY_DATE);

        manager.printResearcherRanking();
    }
}