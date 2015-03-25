-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-03-2015 a las 06:36:41
-- Versión del servidor: 5.6.17
-- Versión de PHP: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `soy_donante`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cita`
--

CREATE TABLE IF NOT EXISTS `cita` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idUser` int(11) NOT NULL,
  `distrito` varchar(350) COLLATE utf8_unicode_ci NOT NULL,
  `fechaCita` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `horaCita` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `nomCitaDonante` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `apeCitaDonante` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `tipoSangre` varchar(75) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `telefono` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idUser` (`idUser`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `cita`
--

INSERT INTO `cita` (`id`, `idUser`, `distrito`, `fechaCita`, `horaCita`, `nomCitaDonante`, `apeCitaDonante`, `tipoSangre`, `email`, `telefono`) VALUES
(1, 1, '', '23/04/2015', '19:30', '', '', 'A+', '', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contacto`
--

CREATE TABLE IF NOT EXISTS `contacto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idUser` int(11) NOT NULL,
  `idAmigo` int(11) NOT NULL,
  `solicitud` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idUser` (`idUser`),
  KEY `idAmigo` (`idAmigo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=16 ;

--
-- Volcado de datos para la tabla `contacto`
--

INSERT INTO `contacto` (`id`, `idUser`, `idAmigo`, `solicitud`) VALUES
(1, 1, 2, 'enviada'),
(2, 2, 1, 'aceptada');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `perfil`
--

CREATE TABLE IF NOT EXISTS `perfil` (
  `nombres` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `apellidos` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `tipoSangre` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `telefono` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `cuentaFace` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `cuentaTwitt` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `cuentaGoogle` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `fondo` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `foto` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idUser` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idUser` (`idUser`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `perfil`
--

INSERT INTO `perfil` (`nombres`, `apellidos`, `tipoSangre`, `email`, `telefono`, `cuentaFace`, `cuentaTwitt`, `cuentaGoogle`, `fondo`, `foto`, `id`, `idUser`) VALUES
('wilson', 'flores', 'A+', 'wilsonft@hol.es', '5219083', 'facebook.com/wilson', '@wilsonft', 'googl.pl/willson', '', '', 1, 2),
('jose3', 'delgado bustamante', 'O-', 'Otro@fff.com', '233443', 'face.com/fdjose', '@jDebu', 'googl.pl/josebus', '', '', 2, 1),
('', '', '', '', '', '', '', '', '', '', 3, 8),
('pepito', '', '', 'dssdds@dssd.com', '', '', '', '', '', '', 4, 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `post`
--

CREATE TABLE IF NOT EXISTS `post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idUser` int(11) NOT NULL,
  `msjSolicitud` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idUser` (`idUser`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=6 ;

--
-- Volcado de datos para la tabla `post`
--

INSERT INTO `post` (`id`, `idUser`, `msjSolicitud`) VALUES
(1, 9, 'Necesito sangre y plaquetas A+,porfavor los que me pudieran ayudar pongase en contacto conmigo, pueden llamar al numero 345677767676'),
(2, 1, 'Necesito sangre y plaquetas B+,porfavor los que me pudieran ayudar pongase en contacto conmigo, pueden llamar al numero 345677767676'),
(3, 1, 'Necesito sangre y plaquetas O+,porfavor los que me pudieran ayudar pongase en contacto conmigo, pueden llamar al numero 5226823'),
(4, 1, 'Necesito sangre y plaquetas AB+,porfavor los que me pudieran ayudar pongase en contacto conmigo, pueden llamar al numero 5432765'),
(5, 1, 'Necesito sangre y plaquetas A+,porfavor los que me pudieran ayudar pongase en contacto conmigo, pueden llamar al numero ');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `respuesta`
--

CREATE TABLE IF NOT EXISTS `respuesta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idUser` int(11) NOT NULL,
  `idPost` int(11) NOT NULL,
  `msjRespuesta` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idPost` (`idPost`),
  KEY `idUser` (`idUser`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=9 ;

--
-- Volcado de datos para la tabla `respuesta`
--

INSERT INTO `respuesta` (`id`, `idUser`, `idPost`, `msjRespuesta`) VALUES
(1, 1, 1, 'Hola yo te ayudo num 1'),
(2, 2, 1, 'Hola yo tmb te puedo ayudar num1'),
(3, 4, 2, 'hola soy el primero en ayudarte num2'),
(4, 1, 2, 'Hola yo tamb te ayudo num2'),
(5, 1, 2, 'Yo igual numero 2'),
(6, 1, 3, 'Yo te ayudo num3'),
(7, 1, 2, 'Yo tmb num2'),
(8, 1, 1, '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=10 ;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `username`, `password`, `email`) VALUES
(1, 'jose', 'admin', 'jose@git.io'),
(2, 'wilsonft', '5219083', 'kenyo@hol.es'),
(4, 'anthony', '123456', 'ete@dd.com'),
(8, 'diego', '54321', 'ar@ap.com'),
(9, 'pepito', '12465', 'dssdds@dssd.com');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cita`
--
ALTER TABLE `cita`
  ADD CONSTRAINT `cita_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `perfil`
--
ALTER TABLE `perfil`
  ADD CONSTRAINT `perfil_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `post`
--
ALTER TABLE `post`
  ADD CONSTRAINT `post_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `respuesta`
--
ALTER TABLE `respuesta`
  ADD CONSTRAINT `respuesta_ibfk_1` FOREIGN KEY (`idPost`) REFERENCES `post` (`id`),
  ADD CONSTRAINT `respuesta_ibfk_2` FOREIGN KEY (`idUser`) REFERENCES `usuario` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
