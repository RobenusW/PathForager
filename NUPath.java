public enum NUPath {
    EI("EI", "Exploring Creative Expression and Innovation"),
    IC("IC", "Interpreting Culture"),
    FQ("FQ", "Conducting Formal and Quantitative Reasoning"),
    SI("SI", "Understanding Societies and Institutions"),
    AD("AD", "Analyzing and Using Data"),
    DD("DD", "Engaging Differences and Diversity"),
    ER("ER", "Employing Ethical Reasoning"),
    ND("ND", "Natural and Designed World"),
    WF("WF", "First Year Writing"),
    WI("WI", "Writing Intensive"),
    WD("WD", "Advanced Writing in the Discipline"),
    EX("EX", "Integration Experience"),
    CE("CE", "Capstone Experience");


    private final String abbreviation;
    private final String description;

    NUPath(String abbreviation, String description) {
        this.abbreviation = abbreviation;
        this.description = description;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getDescription() {
        return description;
    }
}