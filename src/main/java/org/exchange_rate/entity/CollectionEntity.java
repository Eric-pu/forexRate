package org.exchange_rate.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "collection")
public class CollectionEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "datetime")
    private String date;

    @Column(name = "usd_to_ntd")
    private String usdToNtd;

    @Column(name = "rmb_to_ntd")
    private String rmbToNtd;

    @Column(name = "eur_to_usd")
    private String eurToUsd;

    @Column(name = "usd_to_jpy")
    private String usdToJpy;

    @Column(name = "gbp_to_usd")
    private String gbpToUsd;

    @Column(name = "aup_to_usd")
    private String aupToUsd;

    @Column(name = "usd_to_hkd")
    private String usdToHkd;

    @Column(name = "usd_to_rmb")
    private String usdToRmb;

    @Column(name = "usd_to_zar")
    private String usdToZar;

    @Column(name = "nzd_to_usd")
    private String nzdToUsd;

    @Column(name = "cre_dt")
    private LocalDateTime createDt;

    @Column(name = "cre_by")
    private String createBy;

    @Column(name = "up_dt")
    private LocalDateTime updateDt;

    @Column(name = "up_by")
    private String updateBy;

}
