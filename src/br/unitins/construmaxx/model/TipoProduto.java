package br.unitins.construmaxx.model;

public enum TipoProduto {
	PREMOLD(1, "Pre-moldados"), 
	TELHA(2, "Telhas"), 
	BLCSTRUT(3, "Blocos Estruturais"),
	ARGAMS(2, "Argamassas"),
	METAL(2, "Metais"),
	ELETRIC(2, "Eletrica"),
	PISO(2, "Piso"),
	IMPERM(2, "Impermeabilizantes");
	private int value;
	private String label;

	TipoProduto(int value, String label) {
			this.value = value;
			this.label = label;
	}

	public int getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public static Perfil valueOf(int value) {

		for (Perfil perfil : Perfil.values()) {
			if (perfil.getValue() == value)
				return perfil;
		}
		return null;
	}
}
