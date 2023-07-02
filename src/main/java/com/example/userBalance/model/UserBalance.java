package com.example.userBalance.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "user_balance")
public class UserBalance {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private Double balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBalance that = (UserBalance) o;
        return userId.equals(that.userId) && balance.equals(that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, balance);
    }

    @Override
    public String toString() {
        return "UserBalance{" +
                "userId=" + userId +
                ", balance=" + balance +
                '}';
    }

}
