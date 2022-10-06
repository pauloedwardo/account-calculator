FROM mongo

COPY accounts.json /import/accounts.json
CMD mongoimport --host mongo --db account-calculator --collection accounts  --drop --type json --file /import/accounts.json --jsonArray