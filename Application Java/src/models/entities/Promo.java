package models.entities;

import models.enums.DiscountType;
import models.enums.OfferStatus;

import java.util.Date;
import java.util.UUID;

public class Promo {
    private final UUID id;
    private String offerName;
    private String description;
    private Date startDate;
    private Date endDate;
    private DiscountType discountType;
    private String conditions;
    private OfferStatus offerStatus;
    private UUID contractId;

    public Promo(String offerName, String description, Date startDate, Date endDate, DiscountType discountType, String conditions, OfferStatus offerStatus, UUID contractId) {
        this.id = UUID.randomUUID();
        this.offerName = offerName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountType = discountType;
        this.conditions = conditions;
        this.offerStatus = offerStatus;
        this.contractId = contractId;
    }

    public UUID getId() {
        return id;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public UUID getContractId() {
        return contractId;
    }

    public void setContractId(UUID contractId) {
        this.contractId = contractId;
    }
}
