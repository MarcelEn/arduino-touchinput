package schumacher.dennis.visualisierungtouchpad;

/**
 * Definiert die Funktionionen, welche benötigt werden, um die Spannung korrekt umzuwandeln
 */
public interface SpannungsFilter {
  public DoppelPunkt konvertiereSpannungInKoordinate(DoppelPunkt point);
}
