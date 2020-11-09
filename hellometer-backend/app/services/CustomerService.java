package services;

import model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class CustomerService {


    public List<Object[]> getAverageServiceTimePerDayPart(EntityManager em) {
        List<Object[]> averageTtsPerDayPart = null;

        averageTtsPerDayPart = em.createNativeQuery("" +
                "select day_part, sum(tts)/count(*) as average_tts " +
                "from t_customers " +
                "group by day_part " +
                "order by day_part").getResultList();

        return averageTtsPerDayPart;
    }

    public List<Object[]> getAverageServiceTimePerHour(EntityManager em) {
        List<Object[]> averageTtsPerDayPart = null;

        averageTtsPerDayPart = em.createNativeQuery("" +
                "select cast(date_trunc('hour', first_seen) as time) as hours,\n" +
                "sum(tts)/count(*) as average_time\n" +
                "from t_customers\n" +
                "group by 1 " +
                "order by hours;").getResultList();

        return averageTtsPerDayPart;
    }

    public List<Object[]> averageCustomersPerHour(EntityManager em) {
        List<Object[]> customersPerHour = null;

        customersPerHour = em.createNativeQuery("" +
                "select cast(a.hours as time) as time, sum(customers)/count(*) " +
                "from (select date_trunc('hour', first_seen) as hours,\n" +
                "count(customer_number) as customers\n" +
                "from t_customers\n" +
                "group by date_trunc('hour', first_seen)) a\n" +
                "group by cast(a.hours as time)" +
                "order by time;\n" +
                "\n").getResultList();

        return customersPerHour;
    }

    public List<Object[]> averageCustomersPerDayPart(EntityManager em) {
        List<Object[]> customersPerDayPart = null;


        customersPerDayPart = em.createNativeQuery("" +
                "select a.day_part, sum(customers)/count(*) " +
                "from (select day_part, cast(first_seen as date),\n" +
                "count(customer_number) as customers\n" +
                "from t_customers\n" +
//                "where cast(first_seen as date) between start and end " +
                "group by day_part, cast(first_seen as date)) a\n" +
                "group by a.day_part order by a.day_part;\n" +
                "\n").getResultList();

        return customersPerDayPart;
    }

    public void clearData(EntityManager em) {
        em.createNativeQuery("delete from t_customers").executeUpdate();
    }

}
