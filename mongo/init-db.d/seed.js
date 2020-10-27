db.tax.drop();
db.tax.createIndex({location: 1}, {unique: true});
db.tax.insertMany([
    {
        location: 'CA',
        rate: '0.0975',
        exempt: ['food']
    },
    {
        location: 'NY',
        rate: '0.08875',
        exempt: ['food', 'clothing']
    },
    {
        location: 'HK',
        rate: '0.025',
        exempt: []
    }
]);

db.category.drop();
db.category.createIndex({name: 1}, {unique: true});
db.category.insertMany([
    {
        name: 'food',
        include: ['potato chips']
    },
    {
        name: 'clothing',
        include: ['shirt']
    }
])