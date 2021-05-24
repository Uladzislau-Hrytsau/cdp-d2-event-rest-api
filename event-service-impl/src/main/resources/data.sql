CREATE TABLE IF NOT EXISTS EVENT (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(250) NOT NULL,
    place VARCHAR(250) NOT NULL,
    speaker VARCHAR(250) NOT NULL,
    event_type VARCHAR(250) NOT NULL,
    date_time TIMESTAMP NOT NULL
);

INSERT INTO EVENT (id, title, place, speaker, event_type, date_time) VALUES
    (1, 'title1', 'place1', 'speaker1', 'eventType1', '2012-09-17 18:47:52.69'),
    (2, 'title2', 'place2', 'speaker2', 'eventType2', '2012-09-17 18:47:52.69'),
    (3, 'title3', 'place3', 'speaker3', 'eventType3', '2012-09-17 18:47:52.69'),
    (4, 'title4', 'place4', 'speaker4', 'eventType4', '2012-09-17 18:47:52.69'),
    (5, 'title5', 'place5', 'speaker5', 'eventType5', '2012-09-17 18:47:52.69');