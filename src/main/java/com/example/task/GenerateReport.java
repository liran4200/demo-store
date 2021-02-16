package com.example.task;

import com.example.dao.ProductRepository;
import com.example.dao.ReportRepository;
import com.example.entity.Product;
import com.example.entity.Report;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class GenerateReport {
    private static final Logger logger = LoggerFactory.getLogger(GenerateReport.class);

    private ProductRepository productRepository;
    private ReportRepository reportRepository;

    @Autowired
    public GenerateReport(ProductRepository  productRepository, ReportRepository reportRepository ) {
        this.productRepository = productRepository;
        this.reportRepository = reportRepository;
    }

    @Scheduled(fixedRateString ="${report.interval}", initialDelay = 1000)
    public void writeReport() {
        logger.debug("GenerateReport::Write report");
        List<Product> products = this.productRepository.findAll();
        if( products.size() > 0 ) {
            logger.debug("GenerateReport::products:\n" + products);

            Comparator< Product > comparator = shuffle(products);
            Collections.sort(products, comparator);
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
            logger.debug("GenerateReport::products still not exists, skipping");
        }
    }

    private Comparator<Product> shuffle(List<Product> product ){
        // should shuffle by field.
        return new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getId().compareTo(p2.getId());
            }
        };
    }

}
