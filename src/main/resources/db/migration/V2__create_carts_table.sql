CREATE TABLE `store_api`.`carts` (
  `id` BINARY(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  `date_created` DATE NOT NULL DEFAULT (curdate()),
  PRIMARY KEY (`id`));

CREATE TABLE `store_api`.`cart_items` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `cart_id` BINARY(16) NOT NULL,
  `product_id` BIGINT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`id`),

  INDEX `cart_items_products__fk_idx` (`product_id` ASC),
  INDEX `cart_items_carts__fk_idx` (`cart_id` ASC),
  UNIQUE INDEX `cart_product_unique` (`cart_id`, `product_id`),

  CONSTRAINT `cart_items_carts__fk`
    FOREIGN KEY (`cart_id`)
    REFERENCES `store_api`.`carts` (`id`)
    ON DELETE CASCADE,

  CONSTRAINT `cart_items_products__fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `store_api`.`products` (`id`)
    ON DELETE CASCADE
) ENGINE=InnoDB;