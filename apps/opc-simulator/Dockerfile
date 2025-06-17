FROM node:18-alpine

WORKDIR /app

# Copy package.json and install dependencies
COPY package.json ./
RUN npm install

# Copy server files
COPY server.js simple-server.js festo-server.js ./

# Expose OPC-UA port
EXPOSE 4840

# Start the simulation server
CMD ["npm", "start"] 