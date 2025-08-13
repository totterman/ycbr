FROM node:lts-alpine AS node
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .

# Install react-scripts globally
# RUN npm install -g react-scripts

# Build the app
# COPY web-build ./web-build
# RUN npm run build
EXPOSE 4204
CMD ["npx", "expo", "start", "--web", "-p", "4204"]