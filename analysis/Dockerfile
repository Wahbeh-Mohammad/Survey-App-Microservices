FROM node:alpine
WORKDIR /app
COPY ./build ./build
RUN npm install -g serve --silent
EXPOSE 3001
ENTRYPOINT ["serve","-s","build","-l","3001"]