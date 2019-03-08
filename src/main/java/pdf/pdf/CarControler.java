package pdf.pdf;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CarControler {
    @Autowired
    CarServiceImpl carService;

    @Autowired
    CarRepo carRepo;

    @RequestMapping("/")
    public String start() {
        return "Wellcome!!!!!!!";
    }

    @RequestMapping("/get")
    public @ResponseBody List<Car> get(){
        return carService.getAllCars();
    }

    @RequestMapping("/iter")
    public @ResponseBody Iterable<Car> getIter(){
        return carRepo.findAll();
    }


    @RequestMapping("/pdf")
    public String pdf() {
        try {


            /* User home directory location */
            String userHomeDirectory = System.getProperty("user.home");
            /* Output file location */
            String outputFile = userHomeDirectory + File.separatorChar + "JasperTableExample.pdf";

            /* List to hold Items */
            List<Item> listItems = new ArrayList<Item>();

            /* Create Items */
            Item iPhone = new Item();
            iPhone.setName("iPhone 6S");
            iPhone.setPrice(65000.00);

            Item iPad = new Item();
            iPad.setName("iPad Pro");
            iPad.setPrice(70000.00);

            /* Add Items to List */
            listItems.add(iPhone);
            listItems.add(iPad);

            List<Car> carItems = carService.getAllCars();

            /* Convert List to JRBeanCollectionDataSource */
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(carItems);

            /* Map to hold Jasper report Parameters */
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("ItemDataSource", itemsJRBean);

            /* Using compiled version(.jasper) of Jasper report to generate PDF */
            JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/template_Table.jasper", parameters, new JREmptyDataSource());

            /* outputStream to create PDF */
            OutputStream outputStream = new FileOutputStream(new File(outputFile));
            /* Write content to PDF file */
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            System.out.println("File Generated");
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return "You have created report!!!!";
    }

}
