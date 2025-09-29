CREATE TABLE `store_api`.`orders` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `customer_id` BIGINT NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT current_timestamp,
  `total_price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `orders_users__fk_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `orders_users__fk`
    FOREIGN KEY (`customer_id`)
    REFERENCES `store_api`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `store_api`.`order_items` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  `unit_price` DECIMAL(10,2) NOT NULL,
  `quantity` INT NOT NULL,
  `total_price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `order_items_order__fk_idx` (`order_id` ASC) VISIBLE,
  INDEX `order_items_products__fk_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `order_items_order__fk`
    FOREIGN KEY (`order_id`)
    REFERENCES `store_api`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `order_items_products__fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `store_api`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
