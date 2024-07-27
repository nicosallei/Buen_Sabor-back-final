package NetDevops.BuenSabor.dto.articuloInsumo;

public class InsumoStockDto {
    private String denominacion;
    private Integer stockActual;

    public InsumoStockDto(String denominacion, Integer stockActual) {
        this.denominacion = denominacion;
        this.stockActual = stockActual;
    }


    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }
}