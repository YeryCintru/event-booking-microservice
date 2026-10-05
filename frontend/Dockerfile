# ETAPA 1: Bulid de la aplicación de React/Vite
FROM node:20-alpine AS builder
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build

# ETAPA 2: Servidor Web Nginx
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Limpiar archivos por defecto de Nginx
RUN rm -rf ./*

# Copiar el build comopilado de React desde la etapa 1
COPY --from=builder /app/dist .

# Copiar configuración personalizada de Nginx para manejar React Router
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g","daemon off;"]