package com.power.power_pdf.exceptions;

public class MergeRequestCreationException extends RuntimeException{

    public MergeRequestCreationException() {
        super("Erro ao solicitar mesclagem dos arquivos!");
    }

    public MergeRequestCreationException(String message) {
        super(message);
    }
}
