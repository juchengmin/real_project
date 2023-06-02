package com.ju.mall.nosql.elasticsearch.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @projectName: mall
 * @package: com.ju.mall.nosql.elasticsearch.document
 * @className: EsProduct
 * @author: Eric
 * @description: TODO es存储的商品对象
 * @Document: 这是一个元注解，用于标记一个类作为Elasticsearch中的文档。它指示Spring Data Elasticsearch将该类的对象映射到指定的Elasticsearch索引中
 * indexName = "pms": 这是指定文档所属的Elasticsearch索引的名称。在这个例子中，文档将被映射到名为"pms"的索引中
 * type = "product": 这是指定文档的类型的名称。在Elasticsearch 7.x版本及以上，类型不再被强制要求，但仍然可以指定一个类型。在这个例子中，文档将被映射到名为"product"的类型中
 * shards = 1: 这是指定分片数的参数。分片是将索引数据分布在多个节点上的方式，可以提高索引的性能和扩展性。在这个例子中，索引将使用1个分片
 * replicas = 0: 这是指定副本数的参数。副本是索引的复制，可以提高数据的可用性和容错性。在这个例子中，索引将不会有任何副
 * @date: 2023/5/31 21:09
 * @version: 1.0
 */
@Document(indexName = "pms",type = "product",shards = 1,replicas = 0)
public class EsProduct implements Serializable {
    //用于确保序列化和反序列化过程中的版本一致性，以避免可能出现的序列化兼容性问题
    private static final long serialVersionUID = -1L;

    /**
     * @Id 是Java持久化框架中的注解，用于标识一个实体类的主键属性
     */
    @Id
    private Long id;

    /**
     * @Field(type = FieldType.Keyword) 是Elasticsearch中的注解，用于定义文档字段的映射类型。
     * 具体来说，type = FieldType.Keyword 表示字段的数据类型为 "Keyword"，它是一种不会进行分词的字符串类型。当一个字段被映射为 "Keyword" 类型时，它的值将以整体形式进行索引和检索，不会被拆分成单词。
     */
    @Field(type = FieldType.Keyword)
    private String productSn;

    private Long brandId;

    @Field(type = FieldType.Keyword)
    private String brandName;

    private Long productCategoryId;
    @Field(type = FieldType.Keyword)
    private String productCategoryName;
    private String pic;

    /**
     * analyzer = "ik_max_word"：这是指定字段使用的分析器。在这个例子中，使用的分析器是 "ik_max_word"，它是一个中文分析器，用于将文本分解为单词
     * type = FieldType.Text：这是指定字段的数据类型为 "Text"。"Text" 类型用于存储文本数据，并对文本进行分析和索引。
     */
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String name;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String subTitle;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String keywords;

    private BigDecimal price;
    private Integer sale;
    private Integer newStatus;
    private Integer recommandStatus;
    private Integer stock;
    private Integer promotionType;
    private Integer sort;

    /**
     * type = FieldType.Nested：这是指定字段的数据类型为 "Nested"，表示该字段是一个嵌套字段。嵌套字段是一种特殊类型的字段，它可以包含一个或多个子文档，并以独立的方式进行索引和检索。
     */
    @Field(type =FieldType.Nested)
    private List<EsProductAttributeValue> attrValueList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Integer getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Integer newStatus) {
        this.newStatus = newStatus;
    }

    public Integer getRecommandStatus() {
        return recommandStatus;
    }

    public void setRecommandStatus(Integer recommandStatus) {
        this.recommandStatus = recommandStatus;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(Integer promotionType) {
        this.promotionType = promotionType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<EsProductAttributeValue> getAttrValueList() {
        return attrValueList;
    }

    public void setAttrValueList(List<EsProductAttributeValue> attrValueList) {
        this.attrValueList = attrValueList;
    }
}
