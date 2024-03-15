#include "mainwindow.h"
#include "ui_mainwindow.h"
QRegularExpression regex("\t(.+)");

int counter = 0;
int list_size;
QList<double> first;
QList<double> second;
QList<QString> list;

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    QRegularExpression rx("[0123456789.]*");
    QValidator *validator = new QRegularExpressionValidator(rx,this);
    this->ui->lineEdit->setValidator(validator);
    manager = new QNetworkAccessManager();
    connect(manager, &QNetworkAccessManager::finished, this, &MainWindow::replyFinished);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_pushButton_clicked()
{
    date = QDate::currentDate();
    counter = 0;
    switch(date_range){
    case 0: list_size = 30; break;
    case 1: list_size = 48; break;
    case 2: list_size = 30; break;
    default: list_size = 0; break;
    }
    for(int i = 0;i<list_size;i++){
        list.append(QString::number(i));
    }
    for(int i =0;i<list_size/2;i++){
        first.append(i);
        second.append(i);
        x.append(date);
    }
    if(this->ui->lineEdit->text().isEmpty()){
        this->ui->lineEdit->setText("1");
    }
    if(curr1 == 643)
    {
        first.fill(1,list_size/2);
        counter = list_size/2;
    }
    manager->get(QNetworkRequest(QUrl(QString("http://cbrates.rbc.ru/tsv/%1/%2/%3/%4.tsv")
                                                          .arg(curr1).arg(date.year()).arg(date.month()).arg(date.day()))));

}



void MainWindow::replyFinished(QNetworkReply *reply)
{
    if(reply->error()){
        ui->course->setText("ERROR");
    } else {
        QString string = reply->readAll();
        QRegularExpressionMatch match = regex.match(string);
        if (match.hasMatch()) {
            int current_currency;
            if(counter >= list_size/2-1) current_currency = curr2;
            else current_currency = curr1;
            result = match.captured(1);
            if(counter >= list_size/2){
                second[counter-list_size/2] = result.toDouble();
            }else {
                first[counter] = result.toDouble();
                x[list_size/2-counter-1] = date;
            }
            if (current_currency == 643) {
                second.fill(1,list_size/2);
                counter = list_size-1;
            }
            if(counter == list_size-1){
                this->ui->course->setText(QString::number(first[0]/second[0],'f',2));
                this->ui->convertion_result->setText(QString::number(this->ui->course->toPlainText().toDouble()*this->ui->lineEdit->text().toDouble(),'f',2));
                for(int i = list_size/2-1; i>=0; i--){
                    y.append(QString::number(first[i]/second[i],'f',2).toDouble());
                }
                ui->widget->clearGraphs();
                ui->widget->xAxis2->setVisible(false);
                ui->widget->yAxis2->setVisible(false);

                ui->widget->xAxis->setLabel("Date");
                ui->widget->yAxis->setLabel("Course");
                QSharedPointer<QCPAxisTickerDateTime> dateTimeTicker(new QCPAxisTickerDateTime);

                if(date_range == 1 || date_range ==2){
                    dateTimeTicker->setDateTimeFormat("yyyy-MM-dd");
                }
                else dateTimeTicker->setDateTimeFormat("MM-dd");
                ui->widget->xAxis->setTicker(dateTimeTicker);
                QVector<double> xData;
                for (const QDate& date1 : x) {
                    xData << date1.toJulianDay();
                }
                ui->widget->xAxis->setRange(xData.first(),xData.last());
                ui->widget->yAxis->setRange(*std::min_element(y.begin(),y.end()), *std::max_element(y.begin(),y.end()));

                QCPGraph* graph = ui->widget->addGraph(ui->widget->xAxis,ui->widget->yAxis);
                graph->setData(xData,y);


                ui->widget->replot();
                first.clear();
                second.clear();
                list.clear();
                x.clear();
                y.clear();

            }else{
                if(counter == list_size/2-1) date = QDate::currentDate();
                switch(date_range){
                case 0: date = date.addDays(-2); break;
                case 1: date = date.addDays(-15); break;
                case 2: date = date.addMonths(-4); break;
                default: break;
                }
                counter++;
                manager->get(QNetworkRequest(QUrl(QString("http://cbrates.rbc.ru/tsv/%1/%2/%3/%4.tsv")
                                                      .arg(current_currency).arg(date.year()).arg(date.month()).arg(date.day()))));
            }
        }
    }
}



void MainWindow::on_currency1_currentIndexChanged(int index)
{
    switch(index){
    case 0: curr1 = 840; break;
    case 1: curr1 = 978; break;
    case 2: curr1 = 643; break;
    case 3: curr1 = 933; break;
    case 4: curr1 = 980; break;
        default: curr1 = 1;
    }
}


void MainWindow::on_currency2_currentIndexChanged(int index)
{
    switch(index){
    case 1: curr2 = 840; break;
    case 2: curr2 = 978; break;
    case 0: curr2 = 643; break;
    case 3: curr2 = 933; break;
    case 4: curr2 = 980; break;
    default: curr2 = 1;
    }
}


void MainWindow::on_date_range_currentIndexChanged(int index)
{
    date_range = index;
}



