package services;

import dao.PromoDao;
import models.entities.Promo;
import models.enums.DiscountType;
import models.enums.OfferStatus;


import java.util.Date;
import java.util.UUID;

public class PromoService {
    private final PromoDao promoDao;

    public PromoService() {
        this.promoDao = new PromoDao();
    }

    public void add(String offerName, String description, Date startDate, Date endDate, DiscountType discountType, String conditions, OfferStatus offerStatus, UUID contractId) {
        Promo promo = new Promo(offerName, description, startDate, endDate, discountType, conditions, offerStatus, contractId);
        promoDao.addPromo(promo);
    }

    public void edit(UUID promoId, String offerName, String description, Date startDate, Date endDate, DiscountType discountType, String conditions, OfferStatus offerStatus) {
        promoDao.updatePromo(promoId, offerName,description,startDate,endDate,discountType, conditions,offerStatus);
    }

    public void delete(UUID promoId) {
        promoDao.deletePromo(promoId);
    }

    public void display() {
        promoDao.displayPromos();
    }


}
