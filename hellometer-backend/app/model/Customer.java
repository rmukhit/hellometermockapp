package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "t_customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "customer_number")
    private int c_number;
    @Column(name = "day_part")
    private int day_part;
    @Column(name = "first_seen")
    private Timestamp first_seen;
    @Column(name = "tts")
    private int total_time_spent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getC_number() {
        return c_number;
    }

    public void setC_number(int c_number) {
        this.c_number = c_number;
    }

    public int getDay_part() {
        return day_part;
    }

    public void setDay_part(int day_part) {
        this.day_part = day_part;
    }

    public Timestamp getFirst_seen() {
        return first_seen;
    }

    public void setFirst_seen(Timestamp first_seen) {
        this.first_seen = first_seen;
    }

    public int getTotal_time_spent() {
        return total_time_spent;
    }

    public void setTotal_time_spent(int total_time_spent) {
        this.total_time_spent = total_time_spent;
    }
}
