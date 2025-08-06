FROM node:lts-alpine AS node

WORKDIR /app

COPY package.json ./

RUN npm install

COPY . .

CMD ["npm", "run", "dev"]