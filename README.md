# Spot-n-Park

Spot-n-Park es una aplicación web para la gestión de estacionamientos, reservas y pagos, diseñada para facilitar la administración y el uso eficiente de espacios de parqueo.

## Tabla de Contenidos

- [Descripción](#descripción)
- [Tecnologías](#tecnologías)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Instalación](#instalación)
  - [Back-end](#back-end)
  - [Front-end](#front-end)
- [Uso](#uso)
- [Licencia](#licencia)

---

## Descripción

Spot-n-Park permite a los usuarios reservar espacios de estacionamiento, gestionar pagos y administrar diferentes servicios adicionales. El sistema está compuesto por un back-end robusto en Java (Spring Boot) y un front-end moderno en Vue.js.

## Tecnologías

- **Back-end:** Java 17+, Spring Boot, Maven, JPA/Hibernate, JWT, Swagger
- **Front-end:** Vue.js 3, Vite, Tailwind CSS, JavaScript
- **Base de datos:** (Configurable, por defecto H2/MySQL/PostgreSQL)
- **Otros:** REST API, Axios

## Estructura del Proyecto

```
Proyecto-Nuclear-IV/
  ├── back-end/         # API RESTful en Spring Boot
  │   └── proyectonuclear4/
  │       ├── src/
  │       └── pom.xml
  └── front-end/        # Aplicación web en Vue.js
      └── spot-n-park/
          ├── src/
          └── package.json
```

## Instalación

### Back-end

1. **Requisitos previos:**
   - Java 17 o superior
   - Maven

2. **Instalación:**
   ```bash
   cd back-end/proyectonuclear4
   mvn clean install
   mvn spring-boot:run
   ```

3. **Configuración:**
   - Edita `src/main/resources/application.properties` para configurar la base de datos y otros parámetros.

4. **Swagger:**
   - Accede a la documentación de la API en: `http://localhost:8080/swagger-ui.html`

### Front-end

1. **Requisitos previos:**
   - Node.js 16+
   - npm

2. **Instalación:**
   ```bash
   cd front-end/spot-n-park
   npm install
   npm run dev
   ```

3. **Configuración:**
   - Edita los archivos de servicios en `src/services/` para apuntar al endpoint correcto del back-end si es necesario.

## Uso

1. Inicia el back-end y el front-end como se indica arriba.
2. Accede a la aplicación web en `http://localhost:5173` (o el puerto que indique Vite).
3. Regístrate o inicia sesión para comenzar a gestionar reservas, usuarios, pagos y más.

## Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo [LICENSE](front-end/spot-n-park/LICENSE) para más detalles. 
