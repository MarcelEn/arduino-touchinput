int Y;
int X;

void setup() {
  Serial.begin(9600);
  pinMode(13, OUTPUT);
}

void loop() {
  // Lese Y
  bereiteYVor();
  Y = analogRead(A0);

  // Lese X
  bereiteXVor();
  X = analogRead(A1);

  schreibeErgebnis();

  delay(50);
}

void bereiteYVor() {
  pinMode(A0, INPUT);
  pinMode(A1, OUTPUT);
  pinMode(A2, INPUT);
  pinMode(A3, OUTPUT);

  digitalWrite(A1, LOW);
  digitalWrite(A3, HIGH);

  delay(1); // Warten bis Spannung stabil ist
}

void bereiteXVor(){
  pinMode(A0, OUTPUT);
  pinMode(A1, INPUT);
  pinMode(A2, OUTPUT);
  pinMode(A3, INPUT);

  digitalWrite(A0, LOW);
  digitalWrite(A2, HIGH);

  delay(1); // Warten bis Spannung stabil ist
}

void schreibeErgebnis(){
  Serial.print(X);
  Serial.print(";");
  Serial.print(Y);
  Serial.print(";");
  Serial.print("\n");
}
