package model.elements;

public enum Gene {
    GENE_0,
    GENE_1,
    GENE_2,
    GENE_3,
    GENE_4,
    GENE_5,
    GENE_6,
    GENE_7;

    public int getRotation(){
        return ordinal();
    }
}


