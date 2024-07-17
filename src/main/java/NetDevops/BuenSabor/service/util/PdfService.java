package NetDevops.BuenSabor.service.util;


import NetDevops.BuenSabor.entities.ArticuloManufacturado;
import NetDevops.BuenSabor.entities.Cliente;
import NetDevops.BuenSabor.entities.Pedido;
import NetDevops.BuenSabor.entities.PedidoDetalle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.source.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;

@Service
public class PdfService {

    public byte[] createPdfPedido(Pedido pedido, Cliente cliente) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Add content to the document
        document.add(new Paragraph("Numero de Pedido: " + pedido.getId()));

        // Add "Buen Sabor" above the table
        document.add(new Paragraph("Buen Sabor"));

        // Create a table with 4 columns
        Table table = new Table(4);
        table.addCell("Producto");
        table.addCell("Precio");
        table.addCell("Cantidad");
        table.addCell("Total");

        // Set the width and center the table
        table.setWidth(500); // Set the width as needed
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        // Iterate over each PedidoDetalle
        for (PedidoDetalle detalle : pedido.getPedidoDetalle()) {
            // Check if the Articulo is an instance of ArticuloManufacturado
            if (detalle.getArticulo() instanceof ArticuloManufacturado) {
                ArticuloManufacturado articulo = (ArticuloManufacturado) detalle.getArticulo();
                table.addCell(articulo.getDenominacion());
                table.addCell(String.valueOf(articulo.getPrecioVenta()));
                table.addCell(String.valueOf(detalle.getCantidad()));
                table.addCell(String.valueOf(articulo.getPrecioVenta() * detalle.getCantidad()));
            }
        }

        // Add the table to the document
        document.add(table);

        document.add(new Paragraph("Total a pagar: " + pedido.getTotal()));

        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}