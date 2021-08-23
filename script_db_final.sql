-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema projeto_integrador
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema projeto_integrador
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `projeto_integrador` DEFAULT CHARACTER SET utf8 ;
USE `projeto_integrador` ;

-- -----------------------------------------------------
-- Table `projeto_integrador`.`endereco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`endereco` (
  `CEP` VARCHAR(8) NOT NULL,
  `nCasa` INT UNSIGNED NOT NULL,
  `nome` VARCHAR(200) NOT NULL,
  `cidade` VARCHAR(100) NOT NULL,
  `uf` INT NOT NULL,
  `uf_descricao` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`CEP`, `nCasa`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`pessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`pessoa` (
  `CPF` VARCHAR(11) NOT NULL,
  `nome` VARCHAR(60) NOT NULL,
  `data_nascimento` DATE NOT NULL,
  `sexo` VARCHAR(1) NOT NULL,
  `sexo_descricao` VARCHAR(12) NOT NULL,
  `Endereco_CEP` VARCHAR(8) NOT NULL,
  `Endereco_nCasa` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`CPF`),
  INDEX `fk_Pessoa_Endereco1_idx` (`Endereco_CEP` ASC, `Endereco_nCasa` ASC) VISIBLE,
  CONSTRAINT `fk_Pessoa_Endereco1`
    FOREIGN KEY (`Endereco_CEP` , `Endereco_nCasa`)
    REFERENCES `projeto_integrador`.`endereco` (`CEP` , `nCasa`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`usuario` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `senha` VARCHAR(25) NOT NULL,
  `data_criacao` DATETIME NOT NULL,
  `ativo` TINYINT(1) NULL DEFAULT '1',
  `Pessoa_CPF` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_Usuario_Pessoa1_idx` (`Pessoa_CPF` ASC) VISIBLE,
  CONSTRAINT `fk_Usuario_Pessoa1`
    FOREIGN KEY (`Pessoa_CPF`)
    REFERENCES `projeto_integrador`.`pessoa` (`CPF`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`cartao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`cartao` (
  `ID` VARCHAR(16) NOT NULL,
  `saldo` DOUBLE UNSIGNED NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `data_cadastro` DATETIME NOT NULL,
  `Usuario_ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Cartao_Usuario1_idx` (`Usuario_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Cartao_Usuario1`
    FOREIGN KEY (`Usuario_ID`)
    REFERENCES `projeto_integrador`.`usuario` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`novidades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`novidades` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(100) NOT NULL,
  `descricao` VARCHAR(1000) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`pedido` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_pedido` DATETIME NOT NULL,
  `Cartao_ID` VARCHAR(16) NOT NULL,
  `Usuario_ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Pedido_Cartao1_idx` (`Cartao_ID` ASC) VISIBLE,
  INDEX `fk_Pedido_Usuario1_idx` (`Usuario_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Pedido_Cartao1`
    FOREIGN KEY (`Cartao_ID`)
    REFERENCES `projeto_integrador`.`cartao` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Pedido_Usuario1`
    FOREIGN KEY (`Usuario_ID`)
    REFERENCES `projeto_integrador`.`usuario` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`tipopokemon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`tipopokemon` (
  `ID` INT NOT NULL,
  `sigla` VARCHAR(3) NOT NULL,
  `descricao` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`pokemon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`pokemon` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(60) NOT NULL,
  `data_cadastro` DATETIME NOT NULL,
  `estoque` INT UNSIGNED NOT NULL,
  `imagem` VARCHAR(3000) NOT NULL,
  `TipoPokemon_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Pokemon_TipoPokemon1_idx` (`TipoPokemon_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Pokemon_TipoPokemon1`
    FOREIGN KEY (`TipoPokemon_ID`)
    REFERENCES `projeto_integrador`.`tipopokemon` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`pedido_has_pokemon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`pedido_has_pokemon` (
  `Pedido_ID` INT UNSIGNED NOT NULL,
  `Pokemon_ID` INT UNSIGNED NOT NULL,
  `quantidade` INT UNSIGNED NOT NULL,
  `valor_unitario` DOUBLE UNSIGNED NOT NULL,
  PRIMARY KEY (`Pedido_ID`, `Pokemon_ID`),
  INDEX `fk_Pedido_has_Pokemon_Pokemon1_idx` (`Pokemon_ID` ASC) VISIBLE,
  INDEX `fk_Pedido_has_Pokemon_Pedido1_idx` (`Pedido_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Pedido_has_Pokemon_Pedido1`
    FOREIGN KEY (`Pedido_ID`)
    REFERENCES `projeto_integrador`.`pedido` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Pedido_has_Pokemon_Pokemon1`
    FOREIGN KEY (`Pokemon_ID`)
    REFERENCES `projeto_integrador`.`pokemon` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`preco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`preco` (
  `data_vigencia` DATE NOT NULL,
  `Pokemon_ID` INT UNSIGNED NOT NULL,
  `valor` DOUBLE UNSIGNED NOT NULL,
  PRIMARY KEY (`data_vigencia`, `Pokemon_ID`),
  INDEX `fk_Preco_Pokemon1_idx` (`Pokemon_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Preco_Pokemon1`
    FOREIGN KEY (`Pokemon_ID`)
    REFERENCES `projeto_integrador`.`pokemon` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

USE `projeto_integrador`;

DELIMITER $$
USE `projeto_integrador`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `projeto_integrador`.`changeEstoque`
BEFORE INSERT ON `projeto_integrador`.`pedido_has_pokemon`
FOR EACH ROW
BEGIN
select estoque into @est from pokemon where ID = new.Pokemon_ID;

if (@est >= new.quantidade) then
	update pokemon set estoque = (@est - new.quantidade) where ID = new.Pokemon_ID;
else
	set new.Pokemon_ID = null;
    signal sqlstate '45000' set message_text = 'Sem estoque suficiente para essa compra';    
end if;
END$$

USE `projeto_integrador`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `projeto_integrador`.`changeSaldo`
BEFORE INSERT ON `projeto_integrador`.`pedido_has_pokemon`
FOR EACH ROW
BEGIN
	set @total = new.quantidade * new.valor_unitario;

	select p.Cartao_ID into @cartao from pedido p where p.ID = new.Pedido_ID;

	select c.saldo into @saldo from cartao c where c.ID like @cartao;

	if (@saldo >= @total) then
		update cartao set saldo = (@saldo - @total) where ID like @cartao;
	else
		set new.Pedido_ID = null;
        signal sqlstate '45000' set message_text = 'Sem saldo suficiente para essa compra';
	end if;
END$$

USE `projeto_integrador`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `projeto_integrador`.`updateEstoque`
BEFORE UPDATE ON `projeto_integrador`.`pedido_has_pokemon`
FOR EACH ROW
BEGIN
	select estoque into @est from pokemon where ID = old.Pokemon_ID;
    
    update pokemon set estoque = (@est + old.quantidade) where ID = old.Pokemon_ID;
    
    select estoque into @est from pokemon where ID = old.Pokemon_ID;
    
    if (@est >= new.quantidade) then
		update pokemon set estoque = (@est - new.quantidade) where ID = new.Pokemon_ID;
	else
		set new.Pokemon_ID = null;
		signal sqlstate '45000' set message_text = 'Sem estoque suficiente para essa compra';    
	end if;

END$$

USE `projeto_integrador`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `projeto_integrador`.`updateSaldo`
BEFORE UPDATE ON `projeto_integrador`.`pedido_has_pokemon`
FOR EACH ROW
BEGIN
	set @old_total = old.quantidade * old.valor_unitario;

	select p.Cartao_ID into @cartao from pedido p where p.ID = old.Pedido_ID;
    
    select c.saldo into @old_saldo from cartao c where c.ID like @cartao;

	update cartao set saldo = (@old_total + @old_saldo) where ID like @cartao;

	select c.saldo into @saldo from cartao c where c.ID like @cartao;
    
    set @total = new.quantidade * new.valor_unitario;
    
    if (@saldo >= @total) then
		update cartao set saldo = (@saldo - @total) where ID like @cartao;
	else
		set new.Pedido_ID = null;
        signal sqlstate '45000' set message_text = 'Sem saldo suficiente para essa compra';
	end if;
END$$

USE `projeto_integrador`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `projeto_integrador`.`restauraEstoque`
BEFORE DELETE ON `projeto_integrador`.`pedido_has_pokemon`
FOR EACH ROW
BEGIN
	select estoque into @est from pokemon where ID = old.Pokemon_ID;
    
    update pokemon set estoque = (@est + old.quantidade) where ID = old.Pokemon_ID;
END$$

USE `projeto_integrador`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `projeto_integrador`.`restauraSaldo`
BEFORE DELETE ON `projeto_integrador`.`pedido_has_pokemon`
FOR EACH ROW
BEGIN
	set @old_total = old.quantidade * old.valor_unitario;

	select p.Cartao_ID into @cartao from pedido p where p.ID = old.Pedido_ID;
    
    select c.saldo into @old_saldo from cartao c where c.ID like @cartao;

	update cartao set saldo = (@old_total + @old_saldo) where ID like @cartao;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
