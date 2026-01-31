# Use Node.js version 16 as the base image and name this stage "build-stage"
FROM node:lts-alpine AS builder

# Set the working directory inside the container to /app
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install the dependencies specified in package.json
RUN npm install

# Copy all files from the current directory to the working directory in the container
COPY . .

# Build the application using the npm run build script defined in package.json
RUN npm run build

# Use the official Nginx image based on Alpine Linux as the base image
FROM nginx:alpine

# Copy the custom nginx.conf configuration file from the local machine to the container
COPY nginx.conf /etc/nginx/nginx.conf

# Copy the built application files from the build stage to the Nginx HTML directory
COPY --from=builder /app/dist /usr/share/nginx/html/ui

# Expose port 80 to allow traffic to the Nginx server
EXPOSE 7084