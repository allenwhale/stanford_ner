import random
import datetime
class NerHandler:
    def __init__(self, db, ner):
        self.db = db
        self.ner = ner
        NerHandler.inst = self

    def get_one(self):
        return self.db.find_one({'doc.state': {'$exists': False}})

    def do_ner(self, msg):
        return self.ner.Execute(msg)

    def gen_date(self, posts):
        date = posts['post_time']
        year = date.year
        month = date.month
        day = date.day
        week = (date - datetime.datetime(year, 1, 1)).days // 7
        return {
            'year': year,
            'month': month,
            'day': day,
            'week': week
        }

    def update(self, posts, ner, date):
        posts['doc']['state'] = 1
        posts['doc']['NER'] = ner
        posts.update(date)
        self.db.save(posts)

    def do_one(self):
        posts = self.get_one()
        print('get')
        print(posts)
        try:
            ner = self.do_ner(posts['doc']['message'])
            ner = [n.encode('utf-8') for n in ner]
        except:
            ner = []
        print(ner)
        date = self.gen_date(posts)
        self.update(posts, ner, date)

