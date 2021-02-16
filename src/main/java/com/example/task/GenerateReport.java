package com.example.task;

import com.example.dao.ProductRepository;
import com.example.dao.ReportRepository;
import com.example.entity.Product;
import com.example.entity.Report;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.comparing;

@Component
public class GenerateReport {
    private static final Logger logger = LoggerFactory.getLogger(GenerateReport.class);
    private final int MAX_RANDOM = 3;
    private ProductRepository productRepository;
    private ReportRepository reportRepository;

    @Autowired
    public GenerateReport(ProductRepository  productRepository, ReportRepository reportRepository ) {
        this.productRepository = productRepository;
        this.reportRepository = reportRepository;
    }

    @Scheduled(fixedRateString ="${report.interval}", initialDelay = 1000)
    public void writeReport() {
        logger.info("GenerateReport::Write report");
        List<Product> products = this.productRepository.findAll();
        if( products.size() > 0 ) {
            logger.info("GenerateReport::products:\n" + products);
            int number = (int) (Math.random() * MAX_RANDOM);
            logger.info("GenerateReport:: random number: " + number);
            Function f = getFunction(number);
            Comparator<Product> comparator = comparing(f, naturalOrder());

            //Comparator< Product > comparator = shuffle(products);
            Collections.sort(products, comparator);
            logger.info("GenerateReport::sorted products:\n" + products);
            try {
                final ByteArrayOutputStream out = new ByteArrayOutputStream();
                final ObjectMapper mapper = new ObjectMapper();

                mapper.writeValue(out, products);

                byte[] data = out.toByteArray();
                Report r = new Report();
                r.setData(data);
                reportRepository.save(r);
            }catch (IOException e) {
                logger.error(e.getMessage());
            }
        }else {
            logger.info("GenerateReport::products still not exists, skipping");
        }
    }


    public Function<Product, ?> getFunction(int number) {
        switch (number){
            case 0:
                return Product::getName;
            case 1:
                return Product::getId;
            case 2:
                return Product::getDescription;
            case 3:
                return Product::getPrice;
            default:
                return null;
        }
    }

}
