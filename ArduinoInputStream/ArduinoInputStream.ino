int ValueY;
int ValueX;
int Y1 = A0;
int X2 = A1;
int Y2 = A2;
int X1 = A3;

void setup() {
  Serial.begin(9600);
  pinMode(13, OUTPUT);
}

void loop() {
  //-------------------------------------------Read Y
  pinMode(Y1, INPUT);
  pinMode(X2, OUTPUT);
  pinMode(Y2, INPUT);
  pinMode(X1, OUTPUT);

  digitalWrite(X2, LOW);
  digitalWrite(X1, HIGH);
  delay(1); //Warten bis Spannung stabil ist
  ValueY = analogRead(Y1);
  //-------------------------------------------Read X
  pinMode(Y1, OUTPUT);
  pinMode(X2, INPUT);
  pinMode(Y2, OUTPUT);
  pinMode(X1, INPUT);
  digitalWrite(Y1, LOW);
  digitalWrite(Y2, HIGH);
  delay(1); //Warten bis Spannung stabil ist
  ValueX = analogRead(X2);
  Serial.print(ValueX);
  Serial.print(";");
  Serial.print(ValueY);
  Serial.print(";");
  Serial.print("\n");
  
  //--- end
  delay(50);
}
