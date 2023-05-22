package fr.webmaker.restful.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@NamedQueries({
    @NamedQuery(name = "Product.findByRef", query = "from Product where reference = ?1")
})
@Table(name = "PRODUCT", uniqueConstraints = { @UniqueConstraint(columnNames = { "product_id" }) })
public class Product extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	public Long id;

	@Column(name = "reference")
	public String reference;

	@Column(name = "code_Client")
	public String codeClient;

	@Column(name = "code_Barre")
	public String codeBarre;

	@Column(name = "price_HT")
	public Double priceHT;

	@Column(name = "taxe_Rule")
	public Double taxeRule;

	@Column(name = "quantity")
	public Long quantity;

	@Column(name = "package_Width")
	public Integer packageWidth;

	@Column(name = "package_Height")
	public Integer packageHeight;

	@Column(name = "package_Depth")
	public Integer packageDepth;

	@Column(name = "package_Weight")
	public Integer packageWeight;
	
	@Column(name = "delivery_Time_QuantityOK")
	private Integer deliveryTimeQuantityOK;
	
	@Column(name = "delivery_Time_QuantityNOK")
	private Integer deliveryTimeQuantityNOK;
	
	public static Product findByIdOrElseThrow(final Long productId) {
		return Product.<Product>findByIdOptional(productId)
				.orElseThrow(() -> new RuntimeException("produit introuvable"));
	}
	
	public static Product findByRef(final String reference) {
		return find("#Product.findByRef", reference).firstResult();
	}
	
	
}
