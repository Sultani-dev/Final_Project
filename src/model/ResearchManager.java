package model;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ResearchManager {

    private List<Researcher> allResearchers;

    public ResearchManager(List<Researcher> allResearchers) {
        this.allResearchers = allResearchers;
    }

    public void printAllPapers(Comparator<ResearchPaper> comparator) {

        System.out.println("       ALL UNIVERSITY RESEARCH PAPERS");

        allResearchers.stream()
                .flatMap(r -> r.getPapers().stream())
                .sorted(comparator)
                .forEach(System.out::println);
        System.out.println();
    }

    public Optional<Researcher> getTopCitedResearcher() {
        return allResearchers.stream()
                .max(Comparator.comparingInt(Researcher::getTotalCitations));
    }

    public void printResearcherRanking() {

        System.out.println("        RESEARCHER CITATION RANKING");

        allResearchers.stream()
                .sorted(Comparator.comparingInt(Researcher::getTotalCitations).reversed())
                .forEach(r -> System.out.printf(
                        "  %-25s | h-index: %2d | total citations: %d%n",
                        r.getName(), r.getHIndex(), r.getTotalCitations()));

        getTopCitedResearcher().ifPresent(r ->
                System.out.println("Top Cited Researcher: " + r.getName()
                        + " (" + r.getTotalCitations() + " citations)"));
        System.out.println();
    }

    public void addResearcher(Researcher r) {
        allResearchers.add(r);
    }
}