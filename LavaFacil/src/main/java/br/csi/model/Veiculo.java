package br.csi.model;

public class Veiculo {
    private Long id;
    private String placa;
    private String tipo;
    private String nome;
    private Long clienteId;

    public Veiculo() {}

    public Veiculo(String placa, String tipo, String nome, Long clienteId) {
        this.placa = placa;
        this.tipo = tipo;
        this.nome = nome;
        this.clienteId = clienteId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
} 