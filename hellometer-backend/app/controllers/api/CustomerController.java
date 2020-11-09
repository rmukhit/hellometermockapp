package controllers.api;

import akka.stream.javadsl.FileIO;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import model.Customer;
import play.http.HttpEntity;
import play.db.jpa.JPAApi;
import play.libs.Files;
import play.libs.Json;
import play.mvc.*;

import javax.transaction.Transactional;

import services.CustomerService;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.math.*;
import java.util.concurrent.TimeUnit;

public class CustomerController extends Controller {
    @Inject
    private JPAApi jpaApi;
    @Inject
    private CustomerService customerService;


    private class AverageServicePerDayPart {
        private int dayPart;
        private double averageTts;
    }

    private class AverageServicePerHour {
        private String hour;
        private double averageTts;
    }

    private class AverageCustomersPerHour {
        private String hour;
        private int customers;
    }
    private class AverageCustomersPerDayPart {
        private int dayPart;
        private int customers;
    }

//    @Transactional
//    public Result customers(Http.Request request) {
//        return jpaApi.withTransaction(em -> {
//            List<Customer> customers =  null;
//            try {
//                customers = customerService.getCustomers(em);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return ok(Json.toJson(customers));
//        });
//    }

    public Date makeDate(String date) {
        try {
            return new SimpleDateFormat("yyyyy-mm-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }


    @Transactional
    public Result getAverageTtsPerDayPart(Http.Request request) {
        return jpaApi.withTransaction(em -> {
            List<Object[]> averageTimesPerDayPart = null;
            List<AverageServicePerDayPart> newAverageTimesPerDayPart = new ArrayList<>();
            try {
                averageTimesPerDayPart = customerService.getAverageServiceTimePerDayPart(em);
                if (averageTimesPerDayPart != null) {
                    for (Object[] averageTime : averageTimesPerDayPart) {
                        AverageServicePerDayPart newAverageTime = new AverageServicePerDayPart();
                        newAverageTime.dayPart = ((BigDecimal) averageTime[0]).intValue();
                        newAverageTime.averageTts = ((BigDecimal) averageTime[1]).doubleValue();
                        newAverageTimesPerDayPart.add(newAverageTime);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ok(Json.toJson(newAverageTimesPerDayPart));
        });
    }


    @Transactional
    public Result getAverageTtsPerHour(Http.Request request) {
        return jpaApi.withTransaction(em -> {
            List<Object[]> averageTimesPerHour= null;
            List<AverageServicePerHour> newAverageTimesPerHour = new ArrayList<>();
            try {
                averageTimesPerHour = customerService.getAverageServiceTimePerHour(em);
                if (averageTimesPerHour != null) {
                    for (Object[] averageTime : averageTimesPerHour) {
                        AverageServicePerHour newAverageTime = new AverageServicePerHour();
                        newAverageTime.hour = String.valueOf(averageTime[0]);
                        newAverageTime.averageTts = ((BigDecimal) averageTime[1]).doubleValue();
                        newAverageTimesPerHour.add(newAverageTime);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ok(Json.toJson(newAverageTimesPerHour));
        });
    }


    @Transactional
    public Result getAverageCustomersPerHour(Http.Request request) {
        return jpaApi.withTransaction(em -> {
            List<Object[]> averageCustomersPerHour= null;
            List<AverageCustomersPerHour> newAverageCustomerPerHour = new ArrayList<>();
            try {
                averageCustomersPerHour = customerService.averageCustomersPerHour(em);
                if (averageCustomersPerHour != null) {
                    for (Object[] averageCustomers : averageCustomersPerHour) {
                        AverageCustomersPerHour newAverageCustomer = new AverageCustomersPerHour();
                        newAverageCustomer.hour = String.valueOf(averageCustomers[0]);
                        newAverageCustomer.customers = ((BigDecimal) averageCustomers[1]).intValue();
                        newAverageCustomerPerHour.add(newAverageCustomer);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ok(Json.toJson(newAverageCustomerPerHour));
        });
    }

    @Transactional
    public Result getAverageCustomersPerDayPart(Http.Request request) {
        return jpaApi.withTransaction(em -> {
            List<Object[]> averageCustomersPerDayPart = null;
            List<AverageCustomersPerDayPart> newAverageCustomerPerDayPart = new ArrayList<>();
            try {
                averageCustomersPerDayPart = customerService.averageCustomersPerDayPart(em);
                if (averageCustomersPerDayPart != null) {
                    for (Object[] averageCustomers : averageCustomersPerDayPart) {
                        AverageCustomersPerDayPart newAverageCustomer = new AverageCustomersPerDayPart();
                        newAverageCustomer.dayPart = ((BigDecimal) averageCustomers[0]).intValue();
                        newAverageCustomer.customers = ((BigDecimal) averageCustomers[1]).intValue();
                        newAverageCustomerPerDayPart.add(newAverageCustomer);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ok(Json.toJson(newAverageCustomerPerDayPart));
        });
    }


    public Result clearData(Http.Request request) {
        return jpaApi.withTransaction(em -> {

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);

            try {
                customerService.clearData(em);
                response.put("success", true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ok(Json.toJson(response));
        });
    }


    public Result uploadData(Http.Request request) {
        return jpaApi.withTransaction(em -> {

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);

            try {
                customerService.clearData(em);
                Http.MultipartFormData<Files.TemporaryFile> body = request.body().asMultipartFormData();
                Http.MultipartFormData.FilePart<Files.TemporaryFile> filePart = body.getFile("file");

                if (filePart != null) {
                    String fileName = "data.csv";
                    Files.TemporaryFile file = filePart.getRef();
                    file.copyTo(Paths.get(fileName), true);
//                    BufferedReader reader = new BufferedReader(new FileReader("/home/ec2-user/hellometer-backend-1.0/data.csv"));
                    BufferedReader reader = new BufferedReader(new FileReader(fileName));
                    String line = null;
                    line = reader.readLine();
                    String[] headers = line.split(",");
                    int c_number_index = 0;
                    int day_part_index = 0;
                    int time_index = 0;
                    int tts_index = 0;

                    for (int i = 0; i < headers.length; i++){
                        if (headers[i].equals("customer_number")) {
                            c_number_index = i;
                        }
                        if (headers[i].equals("day_part")) {
                            day_part_index = i;
                        }
                        if (headers[i].equals("first_seen_utc")) {
                            time_index = i;
                        }
                        if (headers[i].equals("tts")) {
                            tts_index = i;
                        }
                    }
                    while ((line = reader.readLine()) != null) {
                        Customer newCustomer = new Customer();
                        String[] values = line.split(",");
                        newCustomer.setC_number(Integer.parseInt(values[c_number_index]));
                        newCustomer.setDay_part(Integer.parseInt(values[day_part_index]));
                        long time = Long.parseLong(values[time_index]);

                        Date date = new Date(time*1000);
//                        Date date = new Date(time*1000 - TimeUnit.HOURS.toMillis(7));
                        Timestamp ts = new Timestamp(date.getTime());
                        newCustomer.setFirst_seen(ts);
                        newCustomer.setTotal_time_spent(Integer.parseInt(values[tts_index]));
                        em.persist(newCustomer);
                    }
                    reader.close();

                    response.put("success", true);
                } else {
                    response.put("error", new String[]{"FILE_UPLOAD_ERROR"});
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ok(Json.toJson(response));
        });

    }

    public Result downloadFile(Http.Request request) {
        return jpaApi.withTransaction(em -> {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("maket", null);

            try {

                response.put("success", true);
                java.io.File resultFile = new java.io.File("data.csv");
                java.nio.file.Path path = resultFile.toPath();
                Source<ByteString, ?> source = FileIO.fromPath(path);
                Optional<Long> contentLength = null;
                try {
                    contentLength = Optional.of(java.nio.file.Files.size(path));
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
                return new Result(
                        new ResponseHeader(200, Collections.emptyMap()),
                        new HttpEntity.Streamed(source, contentLength, Optional.of("text/plain")));
            } catch (Exception e) {
                response.put("error", new String[]{e.getMessage()});
            }

            return ok(Json.toJson(response));
        });
    }



}
