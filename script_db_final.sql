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
-- Table `projeto_integrador`.`Endereco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`Endereco` (
  `CEP` VARCHAR(8) NOT NULL,
  `nCasa` INT(4) UNSIGNED NOT NULL,
  `nome` VARCHAR(200) NOT NULL,
  `cidade` VARCHAR(100) NOT NULL,
  `uf` INT NOT NULL,
  `uf_descricao` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`CEP`, `nCasa`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`Pessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`Pessoa` (
  `CPF` VARCHAR(11) NOT NULL,
  `nome` VARCHAR(60) NOT NULL,
  `data_nascimento` DATE NOT NULL,
  `sexo` VARCHAR(1) NOT NULL,
  `sexo_descricao` VARCHAR(12) NOT NULL,
  `Endereco_CEP` VARCHAR(8) NOT NULL,
  `Endereco_nCasa` INT(4) UNSIGNED NOT NULL,
  PRIMARY KEY (`CPF`),
  INDEX `fk_Pessoa_Endereco1_idx` (`Endereco_CEP` ASC, `Endereco_nCasa` ASC) VISIBLE,
  CONSTRAINT `fk_Pessoa_Endereco1`
    FOREIGN KEY (`Endereco_CEP` , `Endereco_nCasa`)
    REFERENCES `projeto_integrador`.`Endereco` (`CEP` , `nCasa`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`Usuario` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `senha` VARCHAR(25) NOT NULL,
  `data_criacao` DATETIME NOT NULL,
  `ativo` TINYINT(1) NULL DEFAULT 1,
  `Pessoa_CPF` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Usuario_Pessoa1_idx` (`Pessoa_CPF` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  CONSTRAINT `fk_Usuario_Pessoa1`
    FOREIGN KEY (`Pessoa_CPF`)
    REFERENCES `projeto_integrador`.`Pessoa` (`CPF`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`Cartao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`Cartao` (
  `ID` VARCHAR(16) NOT NULL,
  `saldo` DOUBLE UNSIGNED NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `data_cadastro` DATETIME NOT NULL,
  `Usuario_ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Cartao_Usuario1_idx` (`Usuario_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Cartao_Usuario1`
    FOREIGN KEY (`Usuario_ID`)
    REFERENCES `projeto_integrador`.`Usuario` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`TipoPokemon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`TipoPokemon` (
  `ID` INT NOT NULL,
  `sigla` VARCHAR(3) NOT NULL,
  `descricao` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`Pokemon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`Pokemon` (
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
    REFERENCES `projeto_integrador`.`TipoPokemon` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`Preco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`Preco` (
  `data_vigencia` DATE NOT NULL,
  `Pokemon_ID` INT UNSIGNED NOT NULL,
  `valor` DOUBLE UNSIGNED NOT NULL,
  PRIMARY KEY (`data_vigencia`, `Pokemon_ID`),
  INDEX `fk_Preco_Pokemon1_idx` (`Pokemon_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Preco_Pokemon1`
    FOREIGN KEY (`Pokemon_ID`)
    REFERENCES `projeto_integrador`.`Pokemon` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`Pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`Pedido` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_pedido` DATETIME NOT NULL,
  `Cartao_ID` VARCHAR(16) NOT NULL,
  `Usuario_ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Pedido_Cartao1_idx` (`Cartao_ID` ASC) VISIBLE,
  INDEX `fk_Pedido_Usuario1_idx` (`Usuario_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Pedido_Cartao1`
    FOREIGN KEY (`Cartao_ID`)
    REFERENCES `projeto_integrador`.`Cartao` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Pedido_Usuario1`
    FOREIGN KEY (`Usuario_ID`)
    REFERENCES `projeto_integrador`.`Usuario` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`Novidades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`Novidades` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(100) NOT NULL,
  `descricao` VARCHAR(1000) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projeto_integrador`.`Pedido_has_Pokemon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_integrador`.`Pedido_has_Pokemon` (
  `Pedido_ID` INT UNSIGNED NOT NULL,
  `Pokemon_ID` INT UNSIGNED NOT NULL,
  `quantidade` INT UNSIGNED NOT NULL,
  `valor_unitario` DOUBLE UNSIGNED NOT NULL,
  PRIMARY KEY (`Pedido_ID`, `Pokemon_ID`),
  INDEX `fk_Pedido_has_Pokemon_Pokemon1_idx` (`Pokemon_ID` ASC) VISIBLE,
  INDEX `fk_Pedido_has_Pokemon_Pedido1_idx` (`Pedido_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Pedido_has_Pokemon_Pedido1`
    FOREIGN KEY (`Pedido_ID`)
    REFERENCES `projeto_integrador`.`Pedido` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Pedido_has_Pokemon_Pokemon1`
    FOREIGN KEY (`Pokemon_ID`)
    REFERENCES `projeto_integrador`.`Pokemon` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
