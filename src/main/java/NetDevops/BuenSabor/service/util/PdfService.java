package NetDevops.BuenSabor.service.util;

import NetDevops.BuenSabor.entities.*;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import org.springframework.stereotype.Service;
import com.itextpdf.layout.element.Table;
import java.io.File;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class PdfService {

    public byte[] createPdfFactura(Pedido pedido, Cliente cliente) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);


        Paragraph emptyParagraph = new Paragraph().setMarginBottom(-10);
        document.add(emptyParagraph);


        String logoPath = "src/main/resources/images/logo.png";
        File logoFile = new File(logoPath);
        if (logoFile.exists()) {
            try {
                ImageData imageData = ImageDataFactory.create(logoPath);
                Image logo = new Image(imageData);
                logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
                document.add(logo);
            } catch (MalformedURLException e) {

                e.printStackTrace();
            }
        }


        Paragraph title = new Paragraph("Detalle Factura")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(22)
                .setMarginBottom(5);
        document.add(title);


        Sucursal sucursal = pedido.getSucursal();
        Domicilio sucursalDomicilio = sucursal.getDomicilio();
        Localidad sucursalLocalidad = sucursalDomicilio.getLocalidad();
        Provincia sucursalProvincia = sucursalLocalidad.getProvincia();

        String formattedDate = pedido.getFechaPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String formattedTime = pedido.getHora().format(DateTimeFormatter.ofPattern("HH:mm"));


        Table headerTable = new Table(new float[]{1, 1, 1});
        headerTable.setWidth(UnitValue.createPercentValue(100));
        headerTable.setMarginBottom(5);


        Paragraph branchInfo = new Paragraph()
                .add("Información de la Sucursal:\n")
                .add("Nombre: " + sucursal.getNombre() + "\n")
                .add("Dirección: " + sucursalDomicilio.getCalle() + " " + sucursalDomicilio.getNumero() + "\n")
                .add("Ubicacion: " + sucursalLocalidad.getNombre() + ", " + sucursalProvincia.getNombre() +", "+sucursalProvincia.getPais().getNombre() + "\n")

                .setTextAlignment(TextAlignment.LEFT)
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        headerTable.addCell(new Cell().add(branchInfo).setBorder(Border.NO_BORDER));


        headerTable.addCell(new Cell().setBorder(Border.NO_BORDER));


        Paragraph orderInfo = new Paragraph()
                .add("Número de Pedido: " + pedido.getId() + "\n")
                .add("Fecha: " + formattedDate + "\n")
                .add("Hora: " + formattedTime + "\n")
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        headerTable.addCell(new Cell().add(orderInfo).setBorder(Border.NO_BORDER));

        document.add(headerTable);


        document.add(new Paragraph("\n"));


        Paragraph clientInfo = new Paragraph()
                .add("Información del Cliente:\n")
                .add("Nombre: " + cliente.getNombre() + " " + cliente.getApellido() + "\n")
                .add("Teléfono: " + cliente.getTelefono() + "\n")
                .add("Email: " + cliente.getEmail() + "\n")
                .setMarginTop(5) // Reduced margin top to decrease space
                .setTextAlignment(TextAlignment.LEFT)
                .setBackgroundColor(new DeviceRgb(230, 230, 230))
                .setPadding(10);
        document.add(clientInfo);


        Paragraph buenSabor = new Paragraph("Detalle Pedido")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(16)
                .setMarginTop(20);
        document.add(buenSabor);


        Table table = new Table(new float[]{4, 2, 1, 2});
        table.setWidth(UnitValue.createPercentValue(100));
        table.addHeaderCell(new Cell().add(new Paragraph("Producto")).setBackgroundColor(new DeviceRgb(200, 200, 200)));
        table.addHeaderCell(new Cell().add(new Paragraph("Precio")).setBackgroundColor(new DeviceRgb(200, 200, 200)));
        table.addHeaderCell(new Cell().add(new Paragraph("Cantidad")).setBackgroundColor(new DeviceRgb(200, 200, 200)));
        table.addHeaderCell(new Cell().add(new Paragraph("Total")).setBackgroundColor(new DeviceRgb(200, 200, 200)));


        double totalCalculado = 0;


        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));


        for (PedidoDetalle detalle : pedido.getPedidoDetalle()) {

            if (detalle.getArticulo() instanceof ArticuloManufacturado) {
                ArticuloManufacturado articulo = (ArticuloManufacturado) detalle.getArticulo();
                table.addCell(new Cell().add(new Paragraph(articulo.getDenominacion())).setBackgroundColor(new DeviceRgb(240, 240, 240)));
                table.addCell(new Cell().add(new Paragraph(currencyFormat.format(articulo.getPrecioVenta()))).setBackgroundColor(new DeviceRgb(240, 240, 240)));

                table.addCell(new Cell().add(new Paragraph(String.valueOf(detalle.getCantidad())).setTextAlignment(TextAlignment.CENTER)).setBackgroundColor(new DeviceRgb(240, 240, 240)));
                double totalFila = articulo.getPrecioVenta() * detalle.getCantidad();
                table.addCell(new Cell().add(new Paragraph(currencyFormat.format(totalFila))).setBackgroundColor(new DeviceRgb(240, 240, 240)));
                totalCalculado += totalFila;
            }
        }


        document.add(table);


        double descuento = totalCalculado - pedido.getTotal();


        Paragraph totals = new Paragraph()
                .add("Descuento realizado: " + currencyFormat.format(descuento) + "\n")
                .add("Total: " + currencyFormat.format(pedido.getTotal()))
                .setTextAlignment(TextAlignment.RIGHT)
                .setBold()
                .setBackgroundColor(new DeviceRgb(255, 235, 235))
                .setPadding(10)
                .setMarginTop(20);
        document.add(totals);

        document.close();
        return byteArrayOutputStream.toByteArray();
    }

}
