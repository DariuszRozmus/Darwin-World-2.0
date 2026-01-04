package model.elements;

import model.map.MapDirection;

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

    public Gene next(){
        int nextOrdinal = (ordinal() + 1) % values().length;
        return values()[nextOrdinal];
    }

    public Gene previous(){
        int nextOrdinal = (ordinal() - 1 +values().length) % values().length;
        return values()[nextOrdinal];
    }

    public Gene nextGene(int shift){
        int nextOrdinal = (ordinal() + shift) % values().length;
        return values()[nextOrdinal];
    }

    @Override
    public String toString() {
        return switch (this){
            case GENE_0 -> "Gene_0";
            case GENE_1 -> "Gene_1";
            case GENE_2 -> "Gene_2";
            case GENE_3 -> "Gene_3";
            case GENE_4 -> "Gene_4";
            case GENE_5 -> "Gene_5";
            case GENE_6 -> "Gene_6";
            case GENE_7 -> "Gene_7";
        };
    }
}


