/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 * <p>
 * Please see distribution for license.
 */
package com.opengamma.strata.market.key;

import com.opengamma.strata.basics.market.MarketDataFeed;
import com.opengamma.strata.basics.market.MarketDataId;
import com.opengamma.strata.basics.market.SimpleMarketDataKey;
import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.finance.credit.reference.IndexReferenceInformation;
import com.opengamma.strata.market.curve.IsdaCreditCurveParRates;
import com.opengamma.strata.market.curve.IsdaYieldCurveParRates;
import com.opengamma.strata.market.id.IsdaIndexCreditCurveParRatesId;
import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableConstructor;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 */
@BeanDefinition(builderScope = "private")
public final class IsdaIndexCreditCurveParRatesKey
    implements SimpleMarketDataKey<IsdaCreditCurveParRates>, ImmutableBean, Serializable {

  @PropertyDefinition(validate = "notNull")
  private final IndexReferenceInformation referenceInformation;

  //-------------------------------------------------------------------------
  public static IsdaIndexCreditCurveParRatesKey of(IndexReferenceInformation referenceInformation) {
    return new IsdaIndexCreditCurveParRatesKey(referenceInformation);
  }

  //-------------------------------------------------------------------------
  @Override
  public Class<IsdaCreditCurveParRates> getMarketDataType() {
    return IsdaCreditCurveParRates.class;
  }

  @Override
  public MarketDataId<IsdaCreditCurveParRates> toMarketDataId(MarketDataFeed marketDataFeed) {
    return IsdaIndexCreditCurveParRatesId.of(referenceInformation);
  }

  @ImmutableConstructor
  private IsdaIndexCreditCurveParRatesKey(IndexReferenceInformation referenceInformation) {
    ArgChecker.notNull(referenceInformation, "referenceInformation");
    this.referenceInformation = referenceInformation;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code IsdaIndexCreditCurveParRatesKey}.
   * @return the meta-bean, not null
   */
  public static IsdaIndexCreditCurveParRatesKey.Meta meta() {
    return IsdaIndexCreditCurveParRatesKey.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(IsdaIndexCreditCurveParRatesKey.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  @Override
  public IsdaIndexCreditCurveParRatesKey.Meta metaBean() {
    return IsdaIndexCreditCurveParRatesKey.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the referenceInformation.
   * @return the value of the property, not null
   */
  public IndexReferenceInformation getReferenceInformation() {
    return referenceInformation;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      IsdaIndexCreditCurveParRatesKey other = (IsdaIndexCreditCurveParRatesKey) obj;
      return JodaBeanUtils.equal(getReferenceInformation(), other.getReferenceInformation());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getReferenceInformation());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("IsdaIndexCreditCurveParRatesKey{");
    buf.append("referenceInformation").append('=').append(JodaBeanUtils.toString(getReferenceInformation()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code IsdaIndexCreditCurveParRatesKey}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code referenceInformation} property.
     */
    private final MetaProperty<IndexReferenceInformation> referenceInformation = DirectMetaProperty.ofImmutable(
        this, "referenceInformation", IsdaIndexCreditCurveParRatesKey.class, IndexReferenceInformation.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "referenceInformation");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -2117930783:  // referenceInformation
          return referenceInformation;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends IsdaIndexCreditCurveParRatesKey> builder() {
      return new IsdaIndexCreditCurveParRatesKey.Builder();
    }

    @Override
    public Class<? extends IsdaIndexCreditCurveParRatesKey> beanType() {
      return IsdaIndexCreditCurveParRatesKey.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code referenceInformation} property.
     * @return the meta-property, not null
     */
    public MetaProperty<IndexReferenceInformation> referenceInformation() {
      return referenceInformation;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -2117930783:  // referenceInformation
          return ((IsdaIndexCreditCurveParRatesKey) bean).getReferenceInformation();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code IsdaIndexCreditCurveParRatesKey}.
   */
  private static final class Builder extends DirectFieldsBeanBuilder<IsdaIndexCreditCurveParRatesKey> {

    private IndexReferenceInformation referenceInformation;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -2117930783:  // referenceInformation
          return referenceInformation;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -2117930783:  // referenceInformation
          this.referenceInformation = (IndexReferenceInformation) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public IsdaIndexCreditCurveParRatesKey build() {
      return new IsdaIndexCreditCurveParRatesKey(
          referenceInformation);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(64);
      buf.append("IsdaIndexCreditCurveParRatesKey.Builder{");
      buf.append("referenceInformation").append('=').append(JodaBeanUtils.toString(referenceInformation));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
