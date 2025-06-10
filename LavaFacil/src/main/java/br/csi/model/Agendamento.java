package br.csi.model;

import java.time.LocalDateTime;

public class Agendamento {
    private Long id;
    private LocalDateTime dataHora;
    private String tipoLavagem; // SIMPLES ou COMPLETA
    private double valor;
    private String status; // AGENDADO, CONCLUIDO, CANCELADO
    private Long veiculoId;
    private Long clienteId;

    public Agendamento() {}

    public Agendamento(LocalDateTime dataHora, String tipoLavagem, double valor, Long veiculoId, Long clienteId) {
        this.dataHora = dataHora;
        this.tipoLavagem = tipoLavagem;
        this.valor = valor;
        this.status = "AGENDADO";
        this.veiculoId = veiculoId;
        this.clienteId = clienteId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getTipoLavagem() {
        return tipoLavagem;
    }

    public void setTipoLavagem(String tipoLavagem) {
        this.tipoLavagem = tipoLavagem;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
} 