/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.engine.marketdata;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableMap;
import com.opengamma.strata.basics.market.MarketDataId;
import com.opengamma.strata.collect.result.Result;

/**
 * The result of building a set of scenario market data, containing the successfully built data and details of
 * any data that could not be built.
 */
@BeanDefinition
public final class ScenarioMarketDataResult implements ImmutableBean {

  /** The market data that was successfully built. */
  @PropertyDefinition(validate = "notNull")
  private final ScenarioMarketData marketData;

  /** Details of failures when building single market data values. */
  @PropertyDefinition(validate = "notNull")
  private final Map<MarketDataId<?>, Result<?>> singleValueFailures;

  /** Details of failures when building time series of market data values. */
  @PropertyDefinition(validate = "notNull")
  private final Map<MarketDataId<?>, Result<?>> timeSeriesFailures;

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ScenarioMarketDataResult}.
   * @return the meta-bean, not null
   */
  public static ScenarioMarketDataResult.Meta meta() {
    return ScenarioMarketDataResult.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(ScenarioMarketDataResult.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static ScenarioMarketDataResult.Builder builder() {
    return new ScenarioMarketDataResult.Builder();
  }

  private ScenarioMarketDataResult(
      ScenarioMarketData marketData,
      Map<MarketDataId<?>, Result<?>> singleValueFailures,
      Map<MarketDataId<?>, Result<?>> timeSeriesFailures) {
    JodaBeanUtils.notNull(marketData, "marketData");
    JodaBeanUtils.notNull(singleValueFailures, "singleValueFailures");
    JodaBeanUtils.notNull(timeSeriesFailures, "timeSeriesFailures");
    this.marketData = marketData;
    this.singleValueFailures = ImmutableMap.copyOf(singleValueFailures);
    this.timeSeriesFailures = ImmutableMap.copyOf(timeSeriesFailures);
  }

  @Override
  public ScenarioMarketDataResult.Meta metaBean() {
    return ScenarioMarketDataResult.Meta.INSTANCE;
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
   * Gets the market data that was successfully built.
   * @return the value of the property, not null
   */
  public ScenarioMarketData getMarketData() {
    return marketData;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets details of failures when building single market data values.
   * @return the value of the property, not null
   */
  public Map<MarketDataId<?>, Result<?>> getSingleValueFailures() {
    return singleValueFailures;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets details of failures when building time series of market data values.
   * @return the value of the property, not null
   */
  public Map<MarketDataId<?>, Result<?>> getTimeSeriesFailures() {
    return timeSeriesFailures;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ScenarioMarketDataResult other = (ScenarioMarketDataResult) obj;
      return JodaBeanUtils.equal(getMarketData(), other.getMarketData()) &&
          JodaBeanUtils.equal(getSingleValueFailures(), other.getSingleValueFailures()) &&
          JodaBeanUtils.equal(getTimeSeriesFailures(), other.getTimeSeriesFailures());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getMarketData());
    hash = hash * 31 + JodaBeanUtils.hashCode(getSingleValueFailures());
    hash = hash * 31 + JodaBeanUtils.hashCode(getTimeSeriesFailures());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("ScenarioMarketDataResult{");
    buf.append("marketData").append('=').append(getMarketData()).append(',').append(' ');
    buf.append("singleValueFailures").append('=').append(getSingleValueFailures()).append(',').append(' ');
    buf.append("timeSeriesFailures").append('=').append(JodaBeanUtils.toString(getTimeSeriesFailures()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ScenarioMarketDataResult}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code marketData} property.
     */
    private final MetaProperty<ScenarioMarketData> marketData = DirectMetaProperty.ofImmutable(
        this, "marketData", ScenarioMarketDataResult.class, ScenarioMarketData.class);
    /**
     * The meta-property for the {@code singleValueFailures} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Map<MarketDataId<?>, Result<?>>> singleValueFailures = DirectMetaProperty.ofImmutable(
        this, "singleValueFailures", ScenarioMarketDataResult.class, (Class) Map.class);
    /**
     * The meta-property for the {@code timeSeriesFailures} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Map<MarketDataId<?>, Result<?>>> timeSeriesFailures = DirectMetaProperty.ofImmutable(
        this, "timeSeriesFailures", ScenarioMarketDataResult.class, (Class) Map.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "marketData",
        "singleValueFailures",
        "timeSeriesFailures");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1116764678:  // marketData
          return marketData;
        case -1633495726:  // singleValueFailures
          return singleValueFailures;
        case -1580093459:  // timeSeriesFailures
          return timeSeriesFailures;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public ScenarioMarketDataResult.Builder builder() {
      return new ScenarioMarketDataResult.Builder();
    }

    @Override
    public Class<? extends ScenarioMarketDataResult> beanType() {
      return ScenarioMarketDataResult.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code marketData} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ScenarioMarketData> marketData() {
      return marketData;
    }

    /**
     * The meta-property for the {@code singleValueFailures} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Map<MarketDataId<?>, Result<?>>> singleValueFailures() {
      return singleValueFailures;
    }

    /**
     * The meta-property for the {@code timeSeriesFailures} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Map<MarketDataId<?>, Result<?>>> timeSeriesFailures() {
      return timeSeriesFailures;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 1116764678:  // marketData
          return ((ScenarioMarketDataResult) bean).getMarketData();
        case -1633495726:  // singleValueFailures
          return ((ScenarioMarketDataResult) bean).getSingleValueFailures();
        case -1580093459:  // timeSeriesFailures
          return ((ScenarioMarketDataResult) bean).getTimeSeriesFailures();
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
   * The bean-builder for {@code ScenarioMarketDataResult}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<ScenarioMarketDataResult> {

    private ScenarioMarketData marketData;
    private Map<MarketDataId<?>, Result<?>> singleValueFailures = ImmutableMap.of();
    private Map<MarketDataId<?>, Result<?>> timeSeriesFailures = ImmutableMap.of();

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(ScenarioMarketDataResult beanToCopy) {
      this.marketData = beanToCopy.getMarketData();
      this.singleValueFailures = ImmutableMap.copyOf(beanToCopy.getSingleValueFailures());
      this.timeSeriesFailures = ImmutableMap.copyOf(beanToCopy.getTimeSeriesFailures());
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1116764678:  // marketData
          return marketData;
        case -1633495726:  // singleValueFailures
          return singleValueFailures;
        case -1580093459:  // timeSeriesFailures
          return timeSeriesFailures;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 1116764678:  // marketData
          this.marketData = (ScenarioMarketData) newValue;
          break;
        case -1633495726:  // singleValueFailures
          this.singleValueFailures = (Map<MarketDataId<?>, Result<?>>) newValue;
          break;
        case -1580093459:  // timeSeriesFailures
          this.timeSeriesFailures = (Map<MarketDataId<?>, Result<?>>) newValue;
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
    public ScenarioMarketDataResult build() {
      return new ScenarioMarketDataResult(
          marketData,
          singleValueFailures,
          timeSeriesFailures);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code marketData} property in the builder.
     * @param marketData  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder marketData(ScenarioMarketData marketData) {
      JodaBeanUtils.notNull(marketData, "marketData");
      this.marketData = marketData;
      return this;
    }

    /**
     * Sets the {@code singleValueFailures} property in the builder.
     * @param singleValueFailures  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder singleValueFailures(Map<MarketDataId<?>, Result<?>> singleValueFailures) {
      JodaBeanUtils.notNull(singleValueFailures, "singleValueFailures");
      this.singleValueFailures = singleValueFailures;
      return this;
    }

    /**
     * Sets the {@code timeSeriesFailures} property in the builder.
     * @param timeSeriesFailures  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder timeSeriesFailures(Map<MarketDataId<?>, Result<?>> timeSeriesFailures) {
      JodaBeanUtils.notNull(timeSeriesFailures, "timeSeriesFailures");
      this.timeSeriesFailures = timeSeriesFailures;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(128);
      buf.append("ScenarioMarketDataResult.Builder{");
      buf.append("marketData").append('=').append(JodaBeanUtils.toString(marketData)).append(',').append(' ');
      buf.append("singleValueFailures").append('=').append(JodaBeanUtils.toString(singleValueFailures)).append(',').append(' ');
      buf.append("timeSeriesFailures").append('=').append(JodaBeanUtils.toString(timeSeriesFailures));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
